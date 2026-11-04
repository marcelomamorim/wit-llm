/* @test    @summary Test useProtocolVersion method of ObjectOutputStream    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testUseProtocolVersionValid();
        testUseProtocolVersionInvalid();
        testUseProtocolVersionNonEmptyStream();
    }

    static void testUseProtocolVersionValid() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Use valid protocol version 1
        oos.useProtocolVersion(1);
        // Use valid protocol version 2
        oos.useProtocolVersion(2);
        oos.close();
    }

    static void testUseProtocolVersionInvalid() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.useProtocolVersion(99); // invalid version
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("unknown version") : "Expected unknown version message";
        }
    }

    static void testUseProtocolVersionNonEmptyStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Write something to make stream non-empty
        oos.writeInt(42);
        try {
            oos.useProtocolVersion(1);
            assert false : "Expected IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("stream non-empty") : "Expected stream non-empty message";
        }
        oos.close();
    }
}
