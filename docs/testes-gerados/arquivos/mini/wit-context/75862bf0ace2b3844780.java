/*
 * @test
 * @summary Deterministic tests for SystemModuleFinders.read(String) method
 * @run main SystemModuleFindersWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Optional;
import java.io.IOException;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsOptionalEmptyForUnknownName();
        testReadReturnsOptionalWithByteBufferForKnownName();
    }

    private static void testReadReturnsOptionalEmptyForUnknownName() throws IOException {
        SystemModuleFinders.SystemModuleReader reader = new SystemModuleFinders.SystemModuleReader();
        Optional<ByteBuffer> result = reader.read("nonexistent-resource-name");
        if (result == null) {
            throw new AssertionError("read() returned null instead of Optional.empty()");
        }
        if (result.isPresent()) {
            throw new AssertionError("read() returned a present Optional for unknown resource name");
        }
    }

    private static void testReadReturnsOptionalWithByteBufferForKnownName() throws IOException {
        SystemModuleFinders.SystemModuleReader reader = new SystemModuleFinders.SystemModuleReader();
        // The known resource name "java.base/module-info.class" is expected to exist in the system image
        Optional<ByteBuffer> result = reader.read("java.base/module-info.class");
        if (result == null) {
            throw new AssertionError("read() returned null instead of Optional with ByteBuffer");
        }
        if (!result.isPresent()) {
            throw new AssertionError("read() returned Optional.empty() for known resource name");
        }
        ByteBuffer buffer = result.get();
        if (buffer == null || buffer.remaining() == 0) {
            throw new AssertionError("ByteBuffer from read() is null or empty");
        }
    }

}
