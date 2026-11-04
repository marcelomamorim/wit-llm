/*
 * @test
 * @summary Test ByteArrayOutputStream.write(int) method behavior
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
        testWriteMultipleBytes();
        testWriteByteOverflow();
    }

    private static void testWriteSingleByte() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x41); // 'A'
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != 0x41) {
            throw new RuntimeException("testWriteSingleByte failed: expected [0x41], got " + java.util.Arrays.toString(result));
        }
    }

    private static void testWriteMultipleBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2);
        baos.write(0x41); // 'A'
        baos.write(0x42); // 'B'
        baos.write(0x43); // 'C' - triggers grow
        byte[] result = baos.toByteArray();
        if (result.length != 3 || result[0] != 0x41 || result[1] != 0x42 || result[2] != 0x43) {
            throw new RuntimeException("testWriteMultipleBytes failed: expected [0x41,0x42,0x43], got " + java.util.Arrays.toString(result));
        }
    }

    private static void testWriteByteOverflow() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x123); // 0x123 = 291 decimal, cast to byte should truncate to 0x23
        byte[] result = baos.toByteArray();
        if (result.length != 1 || result[0] != (byte)0x23) {
            throw new RuntimeException("testWriteByteOverflow failed: expected [0x23], got " + java.util.Arrays.toString(result));
        }
    }
}
