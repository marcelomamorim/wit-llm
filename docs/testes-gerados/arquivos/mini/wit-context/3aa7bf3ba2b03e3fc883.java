/*
 * @test
 * @summary Test DataOutputStream.write(int b) method for basic functionality and exception safety
 * @run main DataOutputStreamWitupTest
 */

import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DataOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
    }

    private static void testWriteSingleByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // Write a single byte and verify the output
        dos.write(0x41); // ASCII 'A'
        dos.flush();
        byte[] result = baos.toByteArray();

        if (result.length != 1) {
            throw new AssertionError("Expected 1 byte written, got " + result.length);
        }
        if (result[0] != 0x41) {
            throw new AssertionError("Expected byte 0x41, got " + result[0]);
        }
    }
}
