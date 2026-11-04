/*
 * @test
 * @summary Checks that the system ModuleFinder locates java.base with a jrt:/ location and returns empty for unknown modules
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.util.Optional;

public class SystemModuleFindersWitupTest {
    public static void main(String[] args) throws Exception {
        ModuleFinder finder = ModuleFinder.ofSystem();

        Optional<ModuleReference> ref = finder.find("java.base");
        if (!ref.isPresent()) {
            throw new RuntimeException("java.base not found by system ModuleFinder");
        }

        Optional<URI> loc = ref.get().location();
        if (!loc.isPresent()) {
            throw new RuntimeException("java.base has no location");
        }
        URI expected = URI.create("jrt:/java.base");
        if (!loc.get().equals(expected)) {
            throw new RuntimeException("Unexpected location: " + loc.get());
        }

        if (finder.find("no.such.module.witup").isPresent()) {
            throw new RuntimeException("Unknown module should not be found");
        }
    }
}
