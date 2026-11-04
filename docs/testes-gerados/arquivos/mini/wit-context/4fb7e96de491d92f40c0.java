/*
 * @test
 * @summary Test ObjectOutputStream.useProtocolVersion method for valid and invalid inputs and state
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUseProtocolVersionValid();
        testUseProtocolVersionInvalidVersion();
        testUseProtocolVersionNonEmptyStream();
    }

    private static void testUseProtocolVersionValid() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Initially handles.size() == 0, so setting protocol version should succeed
        oos.useProtocolVersion(ObjectOutputStream.PROTOCOL_VERSION_1);
        oos.useProtocolVersion(ObjectOutputStream.PROTOCOL_VERSION_2);
        oos.close();
    }

    private static void testUseProtocolVersionInvalidVersion() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.useProtocolVersion(999); // invalid version
            throw new RuntimeException("Expected IllegalArgumentException for unknown version");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            oos.close();
        }
    }

    private static void testUseProtocolVersionNonEmptyStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Write something to make handles non-empty
        oos.writeInt(42);
        try {
            oos.useProtocolVersion(ObjectOutputStream.PROTOCOL_VERSION_1);
            throw new RuntimeException("Expected IllegalStateException for non-empty stream");
        } catch (IllegalStateException ise) {
            // expected
        } finally {
            oos.close();
        }
    }
}
