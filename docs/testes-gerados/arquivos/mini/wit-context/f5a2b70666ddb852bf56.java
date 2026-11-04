/*
 * @test
 * @summary Test ObjectOutputStream.writeUTF(String) for valid and boundary inputs
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteUTFWithEmptyString();
        testWriteUTFWithShortString();
        testWriteUTFWithMaxLengthString();
        testWriteUTFWithNullString();
    }

    private static void testWriteUTFWithEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeUTF("");
        oos.close();
        // If no exception, test passes
    }

    private static void testWriteUTFWithShortString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String s = "Hello, world!";
        oos.writeUTF(s);
        oos.close();
        // If no exception, test passes
    }

    private static void testWriteUTFWithMaxLengthString() throws IOException {
        // Construct a string of length 65535 (max UTF length for DataOutput.writeUTF)
        // Using only ASCII chars to keep length == UTF length
        int maxLength = 65535;
        char[] chars = new char[maxLength];
        for (int i = 0; i < maxLength; i++) {
            chars[i] = 'a';
        }
        String s = new String(chars);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeUTF(s);
        oos.close();
        // If no exception, test passes
    }

    private static void testWriteUTFWithNullString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeUTF(null);
            oos.close();
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
