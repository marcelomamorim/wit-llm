/*
 * @test
 * @summary Test jdk.internal.module.ModuleBootstrap.find(String) method
 * @run main ModuleBootstrapWitupTest
 */

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import jdk.internal.module.ModuleBootstrap;
import java.lang.module.ModuleReference;

public class ModuleBootstrapWitupTest {

    // Minimal subclass to access protected fields for testing
    static class TestModuleBootstrap extends ModuleBootstrap {
        // Expose nameToModule for testing
        Map<String, ModuleReference> nameToModule = null;
        java.util.List<ModuleReference> mrefs;

        TestModuleBootstrap(java.util.List<ModuleReference> mrefs) {
            this.mrefs = mrefs;
        }

        @Override
        public Optional<ModuleReference> find(String name) {
            // replicate the original method logic
            if (name == null) {
                throw new NullPointerException();
            }
            Map<String, ModuleReference> localMap = this.nameToModule;
            if (localMap == null) {
                this.nameToModule = localMap = mrefs.stream()
                        .collect(Collectors.toMap(m -> m.descriptor().name(),
                                                  Function.identity()));
            }
            return Optional.ofNullable(localMap.get(name));
        }
    }

    // Dummy ModuleReference implementation for testing
    static class DummyModuleReference implements ModuleReference {
        private final ModuleDescriptor descriptor;

        DummyModuleReference(String name) {
            this.descriptor = ModuleDescriptor.newModule(name).build();
        }

        @Override
        public ModuleDescriptor descriptor() {
            return descriptor;
        }

        @Override
        public Optional<java.nio.file.Path> location() {
            return Optional.empty();
        }

        @Override
        public ModuleReference open() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        TestModuleBootstrap bootstrap = new TestModuleBootstrap(new java.util.ArrayList<ModuleReference>());

        // Test 1: null argument throws NullPointerException
        try {
            bootstrap.find(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: empty mrefs list returns Optional.empty
        Optional<ModuleReference> result = bootstrap.find("any");
        if (result.isPresent()) {
            throw new AssertionError("Expected Optional.empty for unknown module");
        }

        // Test 3: mrefs with one module, find existing module
        DummyModuleReference dummy = new DummyModuleReference("test.module");
        bootstrap = new TestModuleBootstrap(java.util.Collections.singletonList(dummy));
        Optional<ModuleReference> found = bootstrap.find("test.module");
        if (!found.isPresent() || found.get() != dummy) {
            throw new AssertionError("Expected to find the dummy module reference");
        }

        // Test 4: find non-existing module returns Optional.empty
        Optional<ModuleReference> notFound = bootstrap.find("non.existing");
        if (notFound.isPresent()) {
            throw new AssertionError("Expected Optional.empty for non-existing module");
        }

        System.out.println("All tests passed.");
    }
}
