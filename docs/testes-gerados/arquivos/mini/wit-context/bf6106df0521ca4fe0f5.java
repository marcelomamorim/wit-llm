/*
 * @test
 * @summary Test ObjectOutputStream constructor for basic functionality and exception safety
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testConstructorWithValidStream();
        testConstructorWithNullStream();
    }

    private static void testConstructorWithValidStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Basic sanity check: after construction, writeStreamHeader() is called internally,
        // so the stream should not be empty.
        if (baos.size() == 0) {
            throw new AssertionError("Stream header was not written on construction");
        }
        oos.close();
    }

    private static void testConstructorWithNullStream() {
        try {
            new ObjectOutputStream(null);
            throw new AssertionError("Expected NullPointerException when passing null OutputStream");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            // IOException is declared, but NullPointerException is expected for null argument
            throw new AssertionError("Unexpected IOException instead of NullPointerException", e);
        }
    }
}
