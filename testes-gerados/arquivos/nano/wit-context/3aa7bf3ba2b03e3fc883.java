/*
 * @test
 * @summary Test for DataOutputStream.write(int b) method.
 * @run main DataOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteMethodNormal();
        testWriteMethodWithNullStream();
        testWriteMethodWithClosedStream();
    }

    static void testWriteMethodNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.write(0x41); // ASCII 'A'
        dos.flush();
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new AssertionError("Failed: Normal write test");
        }
    }

    static void testWriteMethodWithNullStream() {
        try {
            DataOutputStream dos = new DataOutputStream(null);
            dos.write(0x42); // ASCII 'B'
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testWriteMethodWithClosedStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.close();
        try {
            dos.write(0x43); // ASCII 'C'
            throw new AssertionError("Expected IOException after close");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
