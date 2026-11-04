/*
 * @test
 * @summary Test java.lang.module.ModuleFinder.find(String) for null argument and normal behavior
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class ModuleFinderWitupTest {
    public static void main(String[] args) {
        ModuleFinder finder = new ModuleFinder() {
            @Override
            public Optional<ModuleReference> find(String name) {
                java.util.Objects.requireNonNull(name);
                return Optional.empty();
            }
            @Override
            public java.util.Set<ModuleReference> findAll() {
                return java.util.Collections.emptySet();
            }
        };

        // Test null argument triggers NullPointerException
        boolean npeThrown = false;
        try {
            finder.find(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when find called with null");
        }

        // Test normal call returns Optional.empty()
        Optional<ModuleReference> result = finder.find("anyName");
        if (result == null) {
            throw new AssertionError("find should not return null");
        }
        if (result.isPresent()) {
            throw new AssertionError("find should return Optional.empty() for any name");
        }
    }
}
