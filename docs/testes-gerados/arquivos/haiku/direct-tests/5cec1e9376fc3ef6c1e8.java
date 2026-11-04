/*
 * @test
 * @summary Verifies ModuleFinder.find(String) returns empty for unknown names and rejects null names
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class ModuleFinderWitupTest {

    public static void main(String[] args) {
        ModuleFinder finder = ModuleFinder.of();

        Optional<ModuleReference> found = finder.find("java.base");
        if (found == null) {
            throw new RuntimeException("find returned null instead of Optional");
        }
        if (found.isPresent()) {
            throw new RuntimeException("Expected empty Optional for empty finder");
        }

        Optional<ModuleReference> other = finder.find("com.example.absent");
        if (other.isPresent()) {
            throw new RuntimeException("Expected empty Optional for absent module");
        }

        boolean npe = false;
        try {
            finder.find(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null name");
        }
    }
}
