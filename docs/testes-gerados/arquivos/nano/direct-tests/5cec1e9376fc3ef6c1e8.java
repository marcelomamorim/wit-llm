/*
 * @test
 * @summary Test the find method of ModuleFinder for various inputs
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class ModuleFinderWitupTest {

    public static void main(String[] args) {
        testFindReturnsEmptyForAnyInput();
        testFindNullInputThrows();
    }

    static void testFindReturnsEmptyForAnyInput() {
        ModuleFinder finder = new ModuleFinder() {
            @Override
            public Optional<ModuleReference> find(String name) {
                return Optional.empty();
            }
        };
        String[] testNames = {"test", "com.example", ""};
        for (String name : testNames) {
            Optional<ModuleReference> result = finder.find(name);
            if (result.isPresent()) {
                throw new AssertionError("Expected empty Optional for name: " + name);
            }
        }
    }

    static void testFindNullInputThrows() {
        ModuleFinder finder = new ModuleFinder() {
            @Override
            public Optional<ModuleReference> find(String name) {
                return Optional.empty();
            }
        };
        try {
            finder.find(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
