/*
 * @test
 * @summary Witup-derived checks for ModuleFinder.find(String) on an empty finder: null name raises NullPointerException and unknown names yield Optional.empty()
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class ModuleFinderWitupTest {

    public static void main(String[] args) {
        ModuleFinder finder = ModuleFinder.of();

        Optional<ModuleReference> absent = finder.find("jdk.witup.absent");
        if (absent == null) {
            throw new RuntimeException("find returned null instead of Optional");
        }
        if (absent.isPresent()) {
            throw new RuntimeException("empty finder unexpectedly found a module");
        }

        Optional<ModuleReference> again = finder.find("jdk.witup.absent");
        if (again.isPresent()) {
            throw new RuntimeException("repeated find returned a module");
        }

        boolean npe = false;
        try {
            finder.find(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("find(null) did not throw NullPointerException");
        }
    }
}
