/*
 * @test
 * @summary Checks ModuleReader.read on the system module finder: present resource, absent resource, and read after close.
 * @run main SystemModuleFindersWitupTest
 */
import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;

public class SystemModuleFindersWitupTest {
    public static void main(String[] args) throws Exception {
        ModuleReference ref = ModuleFinder.ofSystem().find("java.base")
                .orElseThrow(() -> new AssertionError("java.base not found"));
        ModuleReader reader = ref.open();
        try {
            Optional<ByteBuffer> found = reader.read("java/lang/Object.class");
            if (!found.isPresent()) {
                throw new AssertionError("expected resource java/lang/Object.class");
            }
            if (found.get().remaining() <= 0) {
                throw new AssertionError("expected non-empty resource buffer");
            }
            Optional<ByteBuffer> missing = reader.read("no/such/Resource.class");
            if (missing.isPresent()) {
                throw new AssertionError("expected empty Optional for missing resource");
            }
        } finally {
            reader.close();
        }
        try {
            reader.read("java/lang/Object.class");
            throw new AssertionError("expected IOException after close");
        } catch (IOException expected) {
            // expected: reader is closed
        }
    }
}
