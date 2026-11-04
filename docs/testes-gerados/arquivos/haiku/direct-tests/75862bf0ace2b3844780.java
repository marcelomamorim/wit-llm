/*
 * @test
 * @summary Checks ModuleReader.read on the system java.base module, exercising the SystemModuleFinders read path indirectly
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        ModuleReference ref = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new RuntimeException("java.base not found"));

        try (ModuleReader reader = ref.open()) {
            Optional<ByteBuffer> found = reader.read("java/lang/Object.class");
            if (!found.isPresent()) {
                throw new RuntimeException("expected java/lang/Object.class to be present");
            }
            ByteBuffer buf = found.get();
            if (buf.remaining() == 0) {
                throw new RuntimeException("expected non-empty resource buffer");
            }
            if (buf.getInt(0) != 0xCAFEBABE) {
                throw new RuntimeException("expected class file magic number");
            }

            Optional<ByteBuffer> missing = reader.read("no/such/Resource.class");
            if (missing.isPresent()) {
                throw new RuntimeException("expected empty Optional for missing resource");
            }
        }
    }
}
