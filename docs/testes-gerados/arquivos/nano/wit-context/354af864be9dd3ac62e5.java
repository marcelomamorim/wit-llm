/*
 * @test
 * @summary Deterministic tests for jdk.internal.module.ModulePatcher
 * @run main ModulePatcherWitupTest
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) throws Exception {
        testListMethodReturnsStream();
        testPatchIfNeededWithEmptyMap();
        testPatchIfNeededWithNoPatches();
    }

    public static void testListMethodReturnsStream() throws IOException {
        ModulePatcher patcher = new ModulePatcher(Collections.emptyMap());
        Stream<String> result = patcher.list();
        boolean hasElements = result.anyMatch(s -> true);
        if (!hasElements) {
            throw new AssertionError("Expected non-empty stream from list() method");
        }
    }

    public static void testPatchIfNeededWithEmptyMap() throws Exception {
        Map<String, List<String>> emptyMap = new HashMap<>();
        ModulePatcher patcher = new ModulePatcher(emptyMap);
        // Create a dummy ModuleReference with minimal stub
        ModuleReference dummyRef = new DummyModuleReference("dummy") {
            @Override
            public java.lang.module.ModuleDescriptor descriptor() {
                return java.lang.module.ModuleDescriptor.newModule("dummy").build();
            }
        };
        ModuleReference resultRef = patcher.patchIfNeeded(dummyRef);
        if (resultRef != dummyRef) {
            throw new AssertionError("Expected same ModuleReference when no patches are present");
        }
    }

    public static void testPatchIfNeededWithNoPatches() throws Exception {
        Map<String, List<String>> map = new HashMap<>();
        map.put("dummy", List.of(Paths.get("/nonexistent/path")));
        ModulePatcher patcher = new ModulePatcher(map);
        ModuleReference dummyRef = new DummyModuleReference("dummy") {
            @Override
            public java.lang.module.ModuleDescriptor descriptor() {
                return java.lang.module.ModuleDescriptor.newModule("dummy").build();
            }
        };
        ModuleReference resultRef = patcher.patchIfNeeded(dummyRef);
        if (resultRef != dummyRef) {
            throw new AssertionError("Expected same ModuleReference when no patches are applicable");
        }
    }

    // Minimal stub class for ModuleReference
    static abstract class DummyModuleReference extends ModuleReference {
        protected DummyModuleReference(String name) {
            super(java.lang.module.ModuleDescriptor.newModule(name).build());
        }
    }
}
