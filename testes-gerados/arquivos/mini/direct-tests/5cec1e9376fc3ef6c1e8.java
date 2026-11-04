/*
 * @test
 * @summary Test java.lang.module.ModuleFinder.find(String) method
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;
import java.util.Objects;

public class ModuleFinderWitupTest {
    public static void main(String[] args) {
        testFindWithNullName();
        testFindWithValidName();
    }

    private static void testFindWithNullName() {
        try {
            ModuleFinder finder = new ModuleFinder() {
                @Override
                public Optional<ModuleReference> find(String name) {
                    Objects.requireNonNull(name);
                    return Optional.empty();
                }
            };
            finder.find(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testFindWithValidName() {
        ModuleFinder finder = new ModuleFinder() {
            @Override
            public Optional<ModuleReference> find(String name) {
                Objects.requireNonNull(name);
                return Optional.empty();
            }
        };
        Optional<ModuleReference> result = finder.find("test.module");
        if (result == null) {
            throw new RuntimeException("find() returned null instead of Optional.empty()");
        }
        if (result.isPresent()) {
            throw new RuntimeException("Expected empty Optional for module name 'test.module'");
        }
    }
}
