/*
 * @test
 * @summary Verifies the public ByteArrayOutputStream(int size) constructor: negative sizes are rejected with the documented message, and zero or positive sizes yield a usable, growable stream.
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeSizeRejected();
        testNegativeMinValueRejected();
        testZeroCapacityGrowsOnWrite();
        testZeroCapacityGrowsOnBulkWrite();
        testPositiveCapacityStartsEmpty();
        testDefaultConstructorGrowsBeyondInitialCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSizeRejected() {
        try {
            new ByteArrayOutputStream(-1);
            throw new RuntimeException("Expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException e) {
            check("Negative initial size: -1".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeMinValueRejected() {
        try {
            new ByteArrayOutputStream(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(e.getMessage() != null && e.getMessage().startsWith("Negative initial size: "),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testZeroCapacityGrowsOnWrite() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        check(out.size() == 0, "New stream with capacity 0 should be empty");
        out.write(42);
        check(out.size() == 1, "Size should be 1 after one write");
        byte[] data = out.toByteArray();
        check(data.length == 1 && data[0] == 42, "Unexpected content after single write");
    }

    static void testZeroCapacityGrowsOnBulkWrite() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        byte[] input = new byte[100];
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) i;
        }
        out.write(input, 0, input.length);
        check(out.size() == 100, "Size should be 100 after bulk write");
        byte[] data = out.toByteArray();
        check(data.length == 100, "Unexpected array length");
        for (int i = 0; i < data.length; i++) {
            check(data[i] == (byte) i, "Mismatch at index " + i);
        }
    }

    static void testPositiveCapacityStartsEmpty() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        check(out.size() == 0, "Stream with positive capacity should start empty");
        check(out.toByteArray().length == 0, "toByteArray should be empty initially");
    }

    static void testDefaultConstructorGrowsBeyondInitialCapacity() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        check(out.size() == 0, "Default stream should start empty");
        byte[] input = new byte[40];
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) (i + 1);
        }
        out.write(input, 0, input.length);
        check(out.size() == 40, "Size should be 40 after writing 40 bytes");
        byte[] data = out.toByteArray();
        for (int i = 0; i < data.length; i++) {
            check(data[i] == (byte) (i + 1), "Mismatch at index " + i);
        }
    }
}
