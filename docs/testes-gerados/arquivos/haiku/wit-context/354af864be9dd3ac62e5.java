/*
 * @test
 * @summary Checks that ModulePatcher.patchIfNeeded exposes patched resources through the reader list, and returns the original reference when no patch applies
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws Exception {
        ModuleReference javaBase = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new AssertionError("java.base not found"));

        testNoPatchForOtherModuleReturnsSameReference(javaBase);
        testEmptyInputReturnsSameReference(javaBase);
        testPatchedDirectoryResourceIsListed(javaBase);
    }

    private static void testNoPatchForOtherModuleReturnsSameReference(ModuleReference javaBase) {
        Map<String, List<String>> input = new HashMap<>();
        input.put("some.other.module", Collections.singletonList("nonexistent"));
        ModulePatcher patcher = new ModulePatcher(input);
        ModuleReference result = patcher.patchIfNeeded(javaBase);
        if (result != javaBase) {
            throw new AssertionError("expected same reference when module is not patched");
        }
    }

    private static void testEmptyInputReturnsSameReference(ModuleReference javaBase) {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        ModuleReference result = patcher.patchIfNeeded(javaBase);
        if (result != javaBase) {
            throw new AssertionError("expected same reference for empty patch input");
        }
    }

    private static void testPatchedDirectoryResourceIsListed(ModuleReference javaBase) throws IOException {
        Path dir = Files.createTempDirectory("witup-patch");
        Path resource = dir.resolve("jdk").resolve("internal").resolve("witup").resolve("Probe.class");
        Files.createDirectories(resource.getParent());
        Files.write(resource, new byte[] {1, 2, 3});

        Map<String, List<String>> input = new HashMap<>();
        input.put("java.base", Collections.singletonList(dir.toAbsolutePath().toString()));
        ModulePatcher patcher = new ModulePatcher(input);
        ModuleReference patched = patcher.patchIfNeeded(javaBase);

        if (patched == javaBase) {
            throw new AssertionError("expected a new reference for a patched module");
        }

        Set<String> names = new HashSet<>();
        try (ModuleReader reader = patched.open();
             Stream<String> s = reader.list()) {
            names = s.collect(Collectors.toSet());
        }
        if (!names.contains("jdk/internal/witup/Probe.class")) {
            throw new AssertionError("patched resource not listed: " + names);
        }
    }
}
