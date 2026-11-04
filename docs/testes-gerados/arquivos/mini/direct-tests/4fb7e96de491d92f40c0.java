/*
 * @test
 * @summary Test ObjectOutputStream.useProtocolVersion method behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testUseProtocolVersionValid1();
        testUseProtocolVersionValid2();
        testUseProtocolVersionIllegalState();
        testUseProtocolVersionUnknownVersion();
    }

    private static void testUseProtocolVersionValid1() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Should succeed with PROTOCOL_VERSION_1 (1)
        oos.useProtocolVersion(1);
        oos.close();
    }

    private static void testUseProtocolVersionValid2() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Should succeed with PROTOCOL_VERSION_2 (2)
        oos.useProtocolVersion(2);
        oos.close();
    }

    private static void testUseProtocolVersionIllegalState() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Write something to make stream non-empty
        oos.writeInt(123);
        try {
            oos.useProtocolVersion(1);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        } finally {
            oos.close();
        }
    }

    private static void testUseProtocolVersionUnknownVersion() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.useProtocolVersion(99);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        } finally {
            oos.close();
        }
    }
}
