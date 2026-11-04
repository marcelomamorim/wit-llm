/*
 * @test
 * @summary Tests ByteArrayOutputStream.write(int) through public observable behavior
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSingleByteFromZeroCapacity();
        testWriteKeepsLowEightBits();
        testWriteNegativeValueKeepsLowEightBits();
        testWriteGrowsBeyondInitialCapacity();
        testWriteAfterResetStartsAtZero();
    }

    static void testWriteSingleByteFromZeroCapacity() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        out.write(1);
        out.write(2);
        out.write(3);
        byte[] result = out.toByteArray();
        check(out.size() == 3, "size should be 3");
        check(result.length == 3, "length should be 3");
        check(result[0] == 1 && result[1] == 2 && result[2] == 3, "bytes should be 1,2,3");
    }

    static void testWriteKeepsLowEightBits() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(0x1FF);
        byte[] result = out.toByteArray();
        check(out.size() == 1, "size should be 1");
        check(result[0] == (byte) 0xFF, "only low 8 bits should be written");
    }

    static void testWriteNegativeValueKeepsLowEightBits() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(-1);
        byte[] result = out.toByteArray();
        check(out.size() == 1, "size should be 1");
        check(result[0] == (byte) 0xFF, "-1 should be stored as 0xFF");
    }

    static void testWriteGrowsBeyondInitialCapacity() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            out.write(i);
        }
        byte[] result = out.toByteArray();
        check(out.size() == n, "size should be " + n);
        check(result.length == n, "length should be " + n);
        for (int i = 0; i < n; i++) {
            check(result[i] == (byte) i, "byte at index " + i + " should be " + (byte) i);
        }
    }

    static void testWriteAfterResetStartsAtZero() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(2);
        out.write(10);
        out.write(20);
        out.write(30);
        out.reset();
        out.write(77);
        byte[] result = out.toByteArray();
        check(out.size() == 1, "size should be 1 after reset and one write");
        check(result.length == 1 && result[0] == 77, "only the byte written after reset should remain");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
