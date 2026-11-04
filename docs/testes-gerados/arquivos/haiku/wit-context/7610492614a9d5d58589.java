/*
 * @test
 * @summary Checks that ModuleReader.release(ByteBuffer) rejects null and accepts buffers obtained from the system module reader
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws Exception {
        testReleaseNullThrowsNPE();
        testReleaseValidBufferDoesNotThrow();
    }

    static void testReleaseNullThrowsNPE() throws Exception {
        ModuleReference ref = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new AssertionError("java.base not found"));
        try (ModuleReader reader = ref.open()) {
            try {
                reader.release(null);
                throw new AssertionError("expected NullPointerException");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }

    static void testReleaseValidBufferDoesNotThrow() throws Exception {
        ModuleReference ref = ModuleFinder.ofSystem()
                .find("java.base")
                .orElseThrow(() -> new AssertionError("java.base not found"));
        try (ModuleReader reader = ref.open()) {
            Optional<ByteBuffer> bb = reader.read("module-info.class");
            if (!bb.isPresent()) {
                throw new AssertionError("module-info.class not readable from java.base");
            }
            reader.release(bb.get());
        }
    }
}
