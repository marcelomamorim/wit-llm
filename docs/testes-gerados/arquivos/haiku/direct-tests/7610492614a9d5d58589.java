/*
 * @test
 * @summary Checks that ByteBuffers read through the system module reader can be released
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
                .orElseThrow(() -> new AssertionError("java.base not found"));

        try (ModuleReader reader = ref.open()) {
            Optional<ByteBuffer> bbOpt = reader.read("java/lang/Object.class");
            if (!bbOpt.isPresent()) {
                throw new AssertionError("java/lang/Object.class not readable");
            }
            ByteBuffer bb = bbOpt.get();
            if (!bb.hasRemaining()) {
                throw new AssertionError("read buffer is empty");
            }
            reader.release(bb);

            Optional<ByteBuffer> again = reader.read("java/lang/Object.class");
            if (!again.isPresent()) {
                throw new AssertionError("second read failed after release");
            }
            reader.release(again.get());
        }
    }
}
