/*
 * @test
 * @summary Test ObjectOutputStream.writeUTF(String) with normal and edge cases
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteUTFNormalString();
        testWriteUTFEmptyString();
        testWriteUTFNullString();
    }

    private static void testWriteUTFNormalString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String testStr = "Hello, world!";
        oos.writeUTF(testStr);
        oos.flush();
        byte[] data = baos.toByteArray();
        // The first two bytes are length, then UTF bytes
        // Read back using DataInputStream to verify correctness
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        String readBack = dis.readUTF();
        if (!testStr.equals(readBack)) {
            throw new AssertionError("Normal string writeUTF/readUTF mismatch: expected '" + testStr + "' but got '" + readBack + "'");
        }
        oos.close();
    }

    private static void testWriteUTFEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        String testStr = "";
        oos.writeUTF(testStr);
        oos.flush();
        byte[] data = baos.toByteArray();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        String readBack = dis.readUTF();
        if (!testStr.equals(readBack)) {
            throw new AssertionError("Empty string writeUTF/readUTF mismatch: expected empty string but got '" + readBack + "'");
        }
        oos.close();
    }

    private static void testWriteUTFNullString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeUTF(null);
            oos.close();
            throw new AssertionError("Expected NullPointerException when writing null string");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException when writing null string", e);
        }
    }
}
