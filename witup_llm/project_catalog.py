from __future__ import annotations

import ast
import re
from pathlib import Path

from witup_llm.models import MethodDescriptor
from witup_llm.models import ProjectConfig


CLASS_RE = re.compile(r"\b(class|record|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)")
PACKAGE_RE = re.compile(r"^\s*package\s+([A-Za-z0-9_.]+)\s*;", re.MULTILINE)
ANNOTATION_RE = re.compile(r"@\w+(?:\([^)]*\))?\s*")
CONTROL_PREFIXES = (
    "if ",
    "for ",
    "while ",
    "switch ",
    "catch ",
    "return ",
    "throw ",
    "new ",
    "else ",
    "do ",
    "try ",
)


class ProjectCataloger:
    def __init__(self, project: ProjectConfig) -> None:
        self.project = project

    def catalog(self) -> list[MethodDescriptor]:
        files = self._collect_source_files()
        methods: list[MethodDescriptor] = []
        for file_path in files:
            if self.project.language == "java":
                methods.extend(extract_java_methods(file_path, self.project.root))
            elif self.project.language == "python":
                methods.extend(extract_python_methods(file_path, self.project.root))
        return sorted(methods, key=lambda item: (item.file_path, item.start_line, item.method_name))

    def load_overview(self) -> str:
        if self.project.overview_file is None or not self.project.overview_file.exists():
            return ""
        return self.project.overview_file.read_text(encoding="utf-8")

    def _collect_source_files(self) -> list[Path]:
        suffix = ".java" if self.project.language == "java" else ".py"
        files: list[Path] = []
        seen: set[Path] = set()
        for include in self.project.include:
            candidate = (self.project.root / include).resolve()
            if candidate.is_file() and candidate.suffix == suffix and not self._is_excluded(candidate):
                if candidate not in seen:
                    files.append(candidate)
                    seen.add(candidate)
                continue
            if candidate.is_dir():
                for path in candidate.rglob(f"*{suffix}"):
                    resolved = path.resolve()
                    if resolved not in seen and not self._is_excluded(resolved):
                        files.append(resolved)
                        seen.add(resolved)
        return files

    def _is_excluded(self, path: Path) -> bool:
        parts = set(path.parts)
        return any(fragment in parts for fragment in self.project.exclude)


def extract_python_methods(path: Path, project_root: Path) -> list[MethodDescriptor]:
    source = path.read_text(encoding="utf-8")
    tree = ast.parse(source)
    methods: list[MethodDescriptor] = []
    lines = source.splitlines()
    relative_path = safe_relative_path(path, project_root)

    class Visitor(ast.NodeVisitor):
        def __init__(self) -> None:
            self.class_stack: list[str] = []
            self.function_depth = 0

        def visit_ClassDef(self, node: ast.ClassDef) -> None:
            self.class_stack.append(node.name)
            self.generic_visit(node)
            self.class_stack.pop()

        def visit_FunctionDef(self, node: ast.FunctionDef) -> None:
            if self.function_depth == 0:
                methods.append(self._descriptor(node))
            self.function_depth += 1
            self.generic_visit(node)
            self.function_depth -= 1

        def visit_AsyncFunctionDef(self, node: ast.AsyncFunctionDef) -> None:
            if self.function_depth == 0:
                methods.append(self._descriptor(node))
            self.function_depth += 1
            self.generic_visit(node)
            self.function_depth -= 1

        def _descriptor(self, node: ast.FunctionDef | ast.AsyncFunctionDef) -> MethodDescriptor:
            container = ".".join(self.class_stack) if self.class_stack else path.stem
            segment = "\n".join(lines[node.lineno - 1 : node.end_lineno])
            signature = f"{container}.{node.name}()"
            return MethodDescriptor(
                method_id=f"{container}:{node.name}:{node.lineno}",
                file_path=relative_path,
                language="python",
                container_name=container,
                method_name=node.name,
                signature=signature,
                start_line=node.lineno,
                end_line=node.end_lineno or node.lineno,
                source=segment,
            )

    Visitor().visit(tree)
    return methods


def extract_java_methods(path: Path, project_root: Path) -> list[MethodDescriptor]:
    source = path.read_text(encoding="utf-8")
    original_lines = source.splitlines()
    sanitized_lines = sanitize_java_source(source).splitlines()
    relative_path = safe_relative_path(path, project_root)
    package_match = PACKAGE_RE.search(source)
    package = package_match.group(1) if package_match else ""

    methods: list[MethodDescriptor] = []
    class_stack: list[tuple[str, int]] = []
    pending_class_names: list[str] = []
    brace_depth = 0
    index = 0

    while index < len(sanitized_lines):
        line = sanitized_lines[index]
        stripped = line.strip()

        class_names = [match.group(2) for match in CLASS_RE.finditer(line)]
        if class_names:
            if "{" in line:
                for name in class_names:
                    class_stack.append((name, brace_depth + 1))
            else:
                pending_class_names.extend(class_names)
        elif pending_class_names and "{" in line:
            for name in pending_class_names:
                class_stack.append((name, brace_depth + 1))
            pending_class_names.clear()

        if class_stack and looks_like_java_method_start(stripped):
            captured = capture_java_method(
                index=index,
                original_lines=original_lines,
                sanitized_lines=sanitized_lines,
                relative_path=relative_path,
                package=package,
                class_name=class_stack[-1][0],
            )
            if captured is not None:
                methods.append(captured[0])
                index = captured[1]
                continue

        brace_depth += count_braces(line)
        while class_stack and brace_depth < class_stack[-1][1]:
            class_stack.pop()
        index += 1

    return methods


def capture_java_method(
    index: int,
    original_lines: list[str],
    sanitized_lines: list[str],
    relative_path: str,
    package: str,
    class_name: str,
) -> tuple[MethodDescriptor, int] | None:
    header_lines: list[str] = []
    original_header: list[str] = []
    cursor = index
    found_body = False

    while cursor < len(sanitized_lines):
        header_line = sanitized_lines[cursor]
        original_header.append(original_lines[cursor])
        header_lines.append(header_line.strip())
        if ";" in header_line and "{" not in header_line:
            return None
        if "{" in header_line:
            found_body = True
            break
        cursor += 1

    if not found_body:
        return None

    header = " ".join(part for part in header_lines if part).strip()
    method_name = parse_java_method_name(header)
    if method_name is None:
        return None

    body_balance = 0
    body_lines: list[str] = []
    body_cursor = index
    while body_cursor < len(sanitized_lines):
        body_lines.append(original_lines[body_cursor])
        body_balance += count_braces(sanitized_lines[body_cursor])
        body_cursor += 1
        if body_balance == 0:
            break

    if body_balance != 0:
        return None

    container = f"{package}.{class_name}" if package else class_name
    parameters = extract_java_parameter_summary(header)
    signature = f"{container}.{method_name}({parameters})"
    descriptor = MethodDescriptor(
        method_id=f"{container}:{method_name}:{index + 1}",
        file_path=relative_path,
        language="java",
        container_name=container,
        method_name=method_name,
        signature=signature,
        start_line=index + 1,
        end_line=body_cursor,
        source="\n".join(body_lines),
    )
    return descriptor, body_cursor


def parse_java_method_name(header: str) -> str | None:
    normalized = ANNOTATION_RE.sub("", header)
    if normalized.startswith(CONTROL_PREFIXES):
        return None
    if any(token in normalized for token in (" class ", " interface ", " enum ", " record ")):
        return None
    head = normalized.split("(", 1)[0].strip()
    if not head:
        return None
    tokens = head.split()
    if not tokens:
        return None
    candidate = tokens[-1]
    if not re.fullmatch(r"[A-Za-z_][A-Za-z0-9_]*", candidate):
        return None
    return candidate


def extract_java_parameter_summary(header: str) -> str:
    if "(" not in header or ")" not in header:
        return ""
    raw = header.split("(", 1)[1].split(")", 1)[0]
    return " ".join(raw.split())


def looks_like_java_method_start(stripped: str) -> bool:
    if not stripped or "(" not in stripped:
        return False
    lowered = stripped.lower()
    if lowered.startswith(CONTROL_PREFIXES):
        return False
    if lowered.startswith("@") and "{" not in lowered:
        return False
    if lowered.startswith(("class ", "interface ", "enum ", "record ")):
        return False
    return True


def count_braces(line: str) -> int:
    return line.count("{") - line.count("}")


def sanitize_java_source(source: str) -> str:
    result: list[str] = []
    in_block_comment = False
    in_line_comment = False
    in_string = False
    in_char = False
    escape = False
    index = 0
    while index < len(source):
        char = source[index]
        next_char = source[index + 1] if index + 1 < len(source) else ""

        if in_line_comment:
            if char == "\n":
                in_line_comment = False
                result.append(char)
            else:
                result.append(" ")
            index += 1
            continue

        if in_block_comment:
            if char == "*" and next_char == "/":
                in_block_comment = False
                result.extend([" ", " "])
                index += 2
            else:
                result.append("\n" if char == "\n" else " ")
                index += 1
            continue

        if in_string:
            if escape:
                escape = False
            elif char == "\\":
                escape = True
            elif char == '"':
                in_string = False
            result.append("\n" if char == "\n" else " ")
            index += 1
            continue

        if in_char:
            if escape:
                escape = False
            elif char == "\\":
                escape = True
            elif char == "'":
                in_char = False
            result.append("\n" if char == "\n" else " ")
            index += 1
            continue

        if char == "/" and next_char == "/":
            in_line_comment = True
            result.extend([" ", " "])
            index += 2
            continue
        if char == "/" and next_char == "*":
            in_block_comment = True
            result.extend([" ", " "])
            index += 2
            continue
        if char == '"':
            in_string = True
            result.append(" ")
            index += 1
            continue
        if char == "'":
            in_char = True
            result.append(" ")
            index += 1
            continue

        result.append(char)
        index += 1
    return "".join(result)


def safe_relative_path(path: Path, project_root: Path) -> str:
    resolved_path = path.resolve()
    resolved_root = project_root.resolve()
    try:
        return str(resolved_path.relative_to(resolved_root))
    except ValueError:
        return str(path)
