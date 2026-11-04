/*
 * @test
 * @summary Verify that ExplodedModuleReader.list() reports module-info.class for an exploded module and throws IOException after the reader is closed
 * @run main ModuleReferencesWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleReferencesWitupTest {

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("witup-mrefs");
        Path mi = dir.resolve("module-info.class");
        try {
            ModuleDescriptor md = ModuleDescriptor.newModule("m.witup").build();
            try (OutputStream out = Files.newOutputStream(mi)) {
                md.write(out);
            }

            ModuleFinder finder = ModuleFinder.of(dir);
            Optional<ModuleReference> ref = finder.find("m.witup");
            if (!ref.isPresent()) {
                throw new AssertionError("exploded module not found");
            }

            ModuleReader reader = ref.get().open();
            Set<String> names;
            try (Stream<String> s = reader.list()) {
                names = s.collect(Collectors.toSet());
            }
            if (!names.contains("module-info.class")) {
                throw new AssertionError("module-info.class not listed: " + names);
            }

            reader.close();
            try {
                reader.list();
                throw new AssertionError("list() on closed reader did not throw IOException");
            } catch (IOException expected) {
                // expected: reader is closed
            }
        } finally {
            Files.deleteIfExists(mi);
            Files.deleteIfExists(dir);
        }
    }
}
