package catalog

import (
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

var (
	classRE      = regexp.MustCompile(`\b(class|record|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)`)
	packageRE    = regexp.MustCompile(`^\s*package\s+([A-Za-z0-9_.]+)\s*;`)
	javaMethodRE = regexp.MustCompile(`([A-Za-z_][A-Za-z0-9_]*)\s*\(([^)]*)\)\s*(throws\s+[^\{]+)?\{`)
)

// Cataloger discovers Java methods in the configured project.
//
// The implementation is intentionally conservative. It prefers predictable and
// reproducible discovery over trying to support multiple languages with one
// parser. This keeps the research baseline aligned with the current Java-only
// experiment scope.
type Cataloger struct {
	cfg domain.ProjectConfig
}

// NewCataloger builds a cataloger for one project configuration.
func NewCataloger(cfg domain.ProjectConfig) *Cataloger {
	return &Cataloger{cfg: cfg}
}

// Catalog returns all discovered Java methods sorted deterministically.
func (c *Cataloger) Catalog() ([]domain.MethodDescriptor, error) {
	files, err := c.collectSourceFiles()
	if err != nil {
		return nil, err
	}

	methods := make([]domain.MethodDescriptor, 0, 512)
	for _, file := range files {
		discoveredMethods, err := extractJavaMethods(file, c.cfg.Root)
		if err != nil {
			return nil, err
		}
		methods = append(methods, discoveredMethods...)
	}

	sort.Slice(methods, func(i, j int) bool {
		left := methods[i]
		right := methods[j]
		if left.FilePath != right.FilePath {
			return left.FilePath < right.FilePath
		}
		if left.StartLine != right.StartLine {
			return left.StartLine < right.StartLine
		}
		return left.MethodName < right.MethodName
	})
	return methods, nil
}

// LoadOverview reads the optional project overview used in prompt construction.
func (c *Cataloger) LoadOverview() (string, error) {
	if strings.TrimSpace(c.cfg.OverviewFile) == "" {
		return "", nil
	}

	data, err := os.ReadFile(c.cfg.OverviewFile)
	if err != nil {
		return "", fmt.Errorf("read overview file %q: %w", c.cfg.OverviewFile, err)
	}
	return string(data), nil
}

// collectSourceFiles resolves include roots and keeps only Java source files
// that are not excluded by repository policy.
func (c *Cataloger) collectSourceFiles() ([]string, error) {
	seen := map[string]bool{}
	files := make([]string, 0, 1024)

	for _, includeRoot := range c.cfg.Include {
		candidatePath := filepath.Join(c.cfg.Root, includeRoot)
		resolvedPath, err := filepath.Abs(candidatePath)
		if err != nil {
			return nil, fmt.Errorf("resolve include path %q: %w", includeRoot, err)
		}

		info, err := os.Stat(resolvedPath)
		if err != nil {
			continue
		}

		if info.Mode().IsRegular() {
			if c.isJavaSource(resolvedPath) && !c.isExcluded(resolvedPath) && !seen[resolvedPath] {
				files = append(files, resolvedPath)
				seen[resolvedPath] = true
			}
			continue
		}

		err = filepath.WalkDir(resolvedPath, func(path string, entry os.DirEntry, walkErr error) error {
			if walkErr != nil {
				return walkErr
			}
			if entry.IsDir() && c.isExcluded(path) {
				return filepath.SkipDir
			}
			if entry.Type().IsRegular() && c.isJavaSource(path) && !c.isExcluded(path) && !seen[path] {
				files = append(files, path)
				seen[path] = true
			}
			return nil
		})
		if err != nil {
			return nil, fmt.Errorf("walk include directory %q: %w", resolvedPath, err)
		}
	}

	return files, nil
}

func (c *Cataloger) isJavaSource(path string) bool {
	return strings.HasSuffix(path, ".java")
}

// isExcluded applies segment-based exclusion to avoid platform-dependent path
// logic and to keep repository filters easy to reason about.
func (c *Cataloger) isExcluded(path string) bool {
	segments := strings.Split(filepath.ToSlash(path), "/")
	excludedSegments := map[string]bool{}
	for _, item := range c.cfg.Exclude {
		excludedSegments[item] = true
	}
	for _, segment := range segments {
		if excludedSegments[segment] {
			return true
		}
	}
	return false
}

func extractJavaMethods(path, projectRoot string) ([]domain.MethodDescriptor, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, fmt.Errorf("read java file %q: %w", path, err)
	}

	text := string(data)
	lines := strings.Split(text, "\n")

	packageName := ""
	for _, line := range lines {
		if match := packageRE.FindStringSubmatch(line); len(match) > 1 {
			packageName = match[1]
			break
		}
	}

	containerName := strings.TrimSuffix(filepath.Base(path), filepath.Ext(path))
	for _, line := range lines {
		if match := classRE.FindStringSubmatch(line); len(match) > 2 {
			containerName = match[2]
			break
		}
	}
	if packageName != "" {
		containerName = packageName + "." + containerName
	}

	relativePath, _ := filepath.Rel(projectRoot, path)
	relativePath = filepath.ToSlash(relativePath)

	methods := []domain.MethodDescriptor{}
	for index, line := range lines {
		trimmedLine := strings.TrimSpace(line)
		if startsControlFlowBlock(trimmedLine) {
			continue
		}

		match := javaMethodRE.FindStringSubmatch(trimmedLine)
		if len(match) <= 2 {
			continue
		}

		methodName := match[1]
		parameters := strings.Join(strings.Fields(match[2]), " ")
		endLine := findMethodEndLine(lines, index)
		signature := fmt.Sprintf("%s.%s(%s)", containerName, methodName, parameters)

		methods = append(methods, domain.MethodDescriptor{
			MethodID:      fmt.Sprintf("%s:%s:%d", containerName, methodName, index+1),
			FilePath:      relativePath,
			ContainerName: containerName,
			MethodName:    methodName,
			Signature:     signature,
			StartLine:     index + 1,
			EndLine:       endLine,
			Source:        strings.Join(lines[index:endLine], "\n"),
		})
	}

	return methods, nil
}

func startsControlFlowBlock(line string) bool {
	return strings.HasPrefix(line, "if ") ||
		strings.HasPrefix(line, "for ") ||
		strings.HasPrefix(line, "while ") ||
		strings.HasPrefix(line, "switch ")
}

func findMethodEndLine(lines []string, startLine int) int {
	balance := strings.Count(lines[startLine], "{") - strings.Count(lines[startLine], "}")
	endLine := startLine + 1
	for cursor := startLine + 1; cursor < len(lines) && balance > 0; cursor++ {
		balance += strings.Count(lines[cursor], "{")
		balance -= strings.Count(lines[cursor], "}")
		endLine = cursor + 1
	}
	return endLine
}
