/*
 * @test
 * @summary Verify that ModuleReader.list() on a closed system module reader throws IOException (WITUP expath list#481 via public API)
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.util.Optional;
import java.util.stream.Stream;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        Optional<ModuleReference> ref = ModuleFinder.ofSystem().find("java.base");
        if (!ref.isPresent()) {
            throw new RuntimeException("java.base not found in system module finder");
        }

        ModuleReader reader = ref.get().open();
        try (Stream<String> s = reader.list()) {
            if (s == null) {
                throw new RuntimeException("list() returned null for open reader");
            }
            s.count();
        }

        reader.close();

        boolean thrown = false;
        try {
            reader.list();
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("list() on closed reader did not throw IOException");
        }
    }
}
