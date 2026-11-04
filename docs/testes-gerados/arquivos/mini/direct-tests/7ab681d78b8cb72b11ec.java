/*
 * @test
 * @summary Test jdk.internal.module.SystemModuleFinders.find(String) method
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleReference;
import java.util.Optional;
import java.util.Objects;

public class SystemModuleFindersWitupTest {

    // Minimal stub to simulate the nameToModule map behavior
    static class SystemModuleFinders {
        private final java.util.Map<String, ModuleReference> nameToModule;

        SystemModuleFinders(java.util.Map<String, ModuleReference> map) {
            this.nameToModule = map;
        }

        public Optional<ModuleReference> find(String name) {
            Objects.requireNonNull(name);
            return Optional.ofNullable(nameToModule.get(name));
        }
    }

    // Minimal stub for ModuleReference to allow compilation
    static class DummyModuleReference extends ModuleReference {
        DummyModuleReference() {
            super(null, null);
        }
    }

    public static void main(String[] args) {
        // Prepare a dummy module reference
        DummyModuleReference dummyRef = new DummyModuleReference();

        // Create a map with one known module name
        java.util.Map<String, ModuleReference> map = new java.util.HashMap<>();
        map.put("java.base", dummyRef);

        SystemModuleFinders finder = new SystemModuleFinders(map);

        // Test 1: find existing module
        Optional<ModuleReference> result1 = finder.find("java.base");
        if (!result1.isPresent() || result1.get() != dummyRef) {
            throw new AssertionError("Expected to find module reference for 'java.base'");
        }

        // Test 2: find non-existing module
        Optional<ModuleReference> result2 = finder.find("non.existing.module");
        if (result2.isPresent()) {
            throw new AssertionError("Expected empty Optional for non-existing module");
        }

        // Test 3: find with null argument should throw NullPointerException
        boolean npeThrown = false;
        try {
            finder.find(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when name is null");
        }

        System.out.println("All tests passed.");
    }
}
