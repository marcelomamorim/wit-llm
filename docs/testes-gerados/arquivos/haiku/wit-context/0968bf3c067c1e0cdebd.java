/*
 * @test
 * @summary Witup-generated behavioral checks for ByteArrayOutputStream.writeBytes
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesAllBytes();
        testEmptyArray();
        testGrowthBeyondInitialCapacity();
        testAppendsInOrder();
        testNullArrayThrowsNpe();
        testInputMutationDoesNotAffectStream();
    }

    static void testWritesAllBytes() {
        byte[] data = {1, 2, 3, (byte) 0xFF};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(data);
        check(Arrays.equals(data, out.toByteArray()), "writeBytes content mismatch");
        check(out.size() == 4, "writeBytes size mismatch");
    }

    static void testEmptyArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        out.writeBytes(new byte[0]);
        check(out.size() == 0, "empty write changed size");
        check(out.toByteArray().length == 0, "empty write produced data");
    }

    static void testGrowthBeyondInitialCapacity() {
        byte[] big = new byte[1000];
        for (int i = 0; i < big.length; i++) {
            big[i] = (byte) (i * 31);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream(4);
        out.writeBytes(big);
        check(Arrays.equals(big, out.toByteArray()), "growth content mismatch");
        check(out.size() == big.length, "growth size mismatch");
    }

    static void testAppendsInOrder() {
        byte[] first = {10, 20};
        byte[] second = {30, 40, 50};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(first);
        out.writeBytes(second);
        byte[] expected = {10, 20, 30, 40, 50};
        check(Arrays.equals(expected, out.toByteArray()), "append order mismatch");
    }

    static void testNullArrayThrowsNpe() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean thrown = false;
        try {
            out.writeBytes(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "null array did not throw NullPointerException");
        check(out.size() == 0, "null array changed stream state");
    }

    static void testInputMutationDoesNotAffectStream() {
        byte[] data = {7, 8, 9};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(data);
        data[0] = 0;
        check(out.toByteArray()[0] == 7, "stream aliased input array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
