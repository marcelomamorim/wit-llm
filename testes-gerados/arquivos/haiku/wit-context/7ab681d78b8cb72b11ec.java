/*
 * @test
 * @summary Verifies that the system ModuleFinder rejects null names and finds java.base through the public API
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        ModuleFinder finder = ModuleFinder.ofSystem();

        boolean npeThrown = false;
        try {
            finder.find(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("find(null) must throw NullPointerException");
        }

        Optional<ModuleReference> ref = finder.find("java.base");
        if (!ref.isPresent()) {
            throw new RuntimeException("java.base must be found in the system module finder");
        }
        if (!ref.get().descriptor().name().equals("java.base")) {
            throw new RuntimeException("unexpected module name: " + ref.get().descriptor().name());
        }

        Optional<ModuleReference> missing = finder.find("no.such.module.witup");
        if (missing.isPresent()) {
            throw new RuntimeException("unknown module must not be found");
        }
    }
}
