/*
 * @test
 * @summary Verifies ModuleReader.list() of a system module returns its resources and throws IOException after close
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        ModuleReference ref = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new AssertionError("java.base not found"));

        try (ModuleReader reader = ref.open()) {
            List<String> names;
            try (Stream<String> s = reader.list()) {
                names = s.collect(Collectors.toList());
            }
            if (names.isEmpty()) {
                throw new AssertionError("list() returned no resources for java.base");
            }
            if (!names.contains("java/lang/Object.class")) {
                throw new AssertionError("java/lang/Object.class not listed");
            }
        }

        ModuleReader closed = ref.open();
        closed.close();
        try {
            closed.list();
            throw new AssertionError("expected IOException after close");
        } catch (IOException expected) {
            if (!"ModuleReader is closed".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }
}
