/*
 * @test
 * @summary Test DataOutputStream.write(int) method behavior
 * @run main DataOutputStreamWitupTest
 */

import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DataOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
        testWriteMultipleBytesAndCount();
    }

    private static void testWriteSingleByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.write(0x41); // ASCII 'A'
        dos.flush();

        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new AssertionError("write(int) did not write the correct byte");
        }
    }

    private static void testWriteMultipleBytesAndCount() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // Write several bytes one by one
        dos.write(0x10);
        dos.write(0x20);
        dos.write(0x30);
        dos.flush();

        byte[] result = baos.toByteArray();
        if (result.length != 3 || result[0] != 0x10 || result[1] != 0x20 || result[2] != 0x30) {
            throw new AssertionError("write(int) did not write multiple bytes correctly");
        }
    }
}
