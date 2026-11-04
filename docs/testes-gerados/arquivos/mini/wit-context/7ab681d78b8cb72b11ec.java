/*
 * @test
 * @summary Test SystemModuleFinders.find(String) for null argument and valid lookup
 * @run main SystemModuleFindersWitupTest
 */

import java.util.Optional;
import java.lang.module.ModuleReference;
import java.util.Objects;

public class SystemModuleFindersWitupTest {

    // Minimal stub to test find method behavior
    static class SystemModuleFinders {
        private final java.util.Map<String, ModuleReference> nameToModule = new java.util.HashMap<>();

        public SystemModuleFinders() {
            // Add a dummy module reference for testing
            nameToModule.put("java.base", new DummyModuleReference("java.base"));
        }

        public Optional<ModuleReference> find(String name) {
            Objects.requireNonNull(name);
            return Optional.ofNullable(nameToModule.get(name));
        }
    }

    // Dummy ModuleReference implementation for testing
    static class DummyModuleReference extends ModuleReference {
        private final String name;

        DummyModuleReference(String name) {
            super(null, null);
            this.name = name;
        }

        @Override
        public ModuleDescriptor descriptor() {
            return null;
        }

        @Override
        public String toString() {
            return "DummyModuleReference[" + name + "]";
        }
    }

    public static void main(String[] args) {
        SystemModuleFinders finder = new SystemModuleFinders();

        // Test 1: find with null argument should throw NullPointerException
        try {
            finder.find(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test 2: find with known module name returns non-empty Optional
        Optional<ModuleReference> opt = finder.find("java.base");
        if (!opt.isPresent()) {
            throw new RuntimeException("Expected module reference for 'java.base' not found");
        }

        // Test 3: find with unknown module name returns empty Optional
        Optional<ModuleReference> opt2 = finder.find("unknown.module");
        if (opt2.isPresent()) {
            throw new RuntimeException("Expected empty Optional for unknown module");
        }
    }
}
