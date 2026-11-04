/*
 * @test
 * @summary Behavioral checks for ByteArrayOutputStream.write(int): low-byte storage, growth from zero capacity and content retrieval
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteStoresLowByte();
        testGrowthFromZeroCapacity();
        testWriteAfterCloseStillWorks();
    }

    private static void testWriteStoresLowByte() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(0x1FF);
        out.write(-1);
        out.write(0x41);
        byte[] result = out.toByteArray();
        check(result.length == 3, "expected 3 bytes but was " + result.length);
        check(result[0] == (byte) 0xFF, "expected low byte 0xFF for 0x1FF");
        check(result[1] == (byte) 0xFF, "expected low byte 0xFF for -1");
        check(result[2] == (byte) 0x41, "expected byte 0x41");
        check(out.size() == 3, "size mismatch");
    }

    private static void testGrowthFromZeroCapacity() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            out.write(i);
        }
        check(out.size() == n, "expected size " + n + " but was " + out.size());
        byte[] result = out.toByteArray();
        check(result.length == n, "array length mismatch");
        for (int i = 0; i < n; i++) {
            check(result[i] == (byte) i, "content mismatch at index " + i);
        }
    }

    private static void testWriteAfterCloseStillWorks() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4);
        out.write(7);
        out.close();
        out.write(8);
        byte[] result = out.toByteArray();
        check(result.length == 2, "expected 2 bytes after close and write");
        check(result[0] == 7 && result[1] == 8, "unexpected content after close");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
