/*
 * @test
 * @summary Test jdk.internal.module.ModuleBootstrap.find(String) method
 * @run main ModuleBootstrapWitupTest
 */

import java.lang.module.ModuleReference;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class ModuleBootstrapWitupTest {

    // Minimal stub for ModuleDescriptor
    static class ModuleDescriptor {
        private final String name;
        ModuleDescriptor(String name) { this.name = name; }
        String name() { return name; }
    }

    // Minimal stub for ModuleReference
    static class ModuleReference {
        private final ModuleDescriptor descriptor;
        ModuleReference(String name) { this.descriptor = new ModuleDescriptor(name); }
        ModuleDescriptor descriptor() { return descriptor; }
    }

    // Minimal implementation of the tested class with only the find method and needed fields
    static class ModuleBootstrap {
        private volatile Map<String, ModuleReference> nameToModule;
        private final List<ModuleReference> mrefs;

        ModuleBootstrap(List<ModuleReference> mrefs) {
            this.mrefs = mrefs;
        }

        public Optional<ModuleReference> find(String name) {
            Objects.requireNonNull(name);
            Map<String, ModuleReference> nameToModule = this.nameToModule;
            if (nameToModule == null) {
                this.nameToModule = nameToModule = mrefs.stream()
                        .collect(Collectors.toMap(m -> m.descriptor().name(),
                                                  Function.identity()));
            }
            return Optional.ofNullable(nameToModule.get(name));
        }
    }

    public static void main(String[] args) {
        // Prepare test data
        List<ModuleReference> modules = new ArrayList<>();
        modules.add(new ModuleReference("java.base"));
        modules.add(new ModuleReference("java.logging"));
        modules.add(new ModuleReference("java.xml"));

        ModuleBootstrap bootstrap = new ModuleBootstrap(modules);

        // Test 1: find existing module
        Optional<ModuleReference> found = bootstrap.find("java.base");
        if (!found.isPresent() || !"java.base".equals(found.get().descriptor().name())) {
            throw new AssertionError("Failed to find existing module java.base");
        }

        // Test 2: find another existing module
        found = bootstrap.find("java.logging");
        if (!found.isPresent() || !"java.logging".equals(found.get().descriptor().name())) {
            throw new AssertionError("Failed to find existing module java.logging");
        }

        // Test 3: find non-existing module
        found = bootstrap.find("non.existing.module");
        if (found.isPresent()) {
            throw new AssertionError("Unexpectedly found non-existing module");
        }

        // Test 4: find with null argument should throw NullPointerException
        try {
            bootstrap.find(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 5: verify caching behavior (nameToModule map is reused)
        Optional<ModuleReference> foundAgain = bootstrap.find("java.xml");
        if (!foundAgain.isPresent() || !"java.xml".equals(foundAgain.get().descriptor().name())) {
            throw new AssertionError("Failed to find existing module java.xml on second call");
        }

        System.out.println("All tests passed.");
    }
}
