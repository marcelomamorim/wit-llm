/*
 * @test
 * @summary Test ObjectOutputStream.writeChars(String) for normal and boundary cases
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteCharsNormal();
        testWriteCharsEmptyString();
        testWriteCharsNull();
    }

    private static void testWriteCharsNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String testString = "Hello, world!";
        oos.writeChars(testString);
        oos.flush();
        oos.close();
        // No exception expected, output should not be empty
        if (baos.size() == 0) {
            throw new AssertionError("Output stream is empty after writeChars with normal string");
        }
    }

    private static void testWriteCharsEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String empty = "";
        oos.writeChars(empty);
        oos.flush();
        oos.close();
        // No exception expected, output may be empty
        if (baos.size() != 0) {
            throw new AssertionError("Output stream should be empty after writeChars with empty string");
        }
    }

    private static void testWriteCharsNull() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeChars(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException thrown: " + e);
        }
    }
}
