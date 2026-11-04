/*
 * @test
 * @summary Functional tests for ByteArrayOutputStream.writeBytes(byte[])
 * @run main ByteArrayOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteArrayOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesAllBytes();
        testAppendsAcrossCalls();
        testEmptyArrayWritesNothing();
        testGrowsBeyondInitialCapacity();
        testNullArrayThrowsNPE();
        testReturnedArrayIsCopy();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testWritesAllBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = {1, 2, 3, (byte) 0xFF};
        out.writeBytes(data);
        check(Arrays.equals(data, out.toByteArray()), "writeBytes must write all bytes");
    }

    static void testAppendsAcrossCalls() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{1, 2, 3, (byte) 0xFF});
        out.writeBytes(new byte[]{9});
        byte[] expected = {1, 2, 3, (byte) 0xFF, 9};
        check(Arrays.equals(expected, out.toByteArray()), "writeBytes must append to existing content");
    }

    static void testEmptyArrayWritesNothing() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{7});
        out.writeBytes(new byte[0]);
        check(Arrays.equals(new byte[]{7}, out.toByteArray()), "empty array must not change content");
    }

    static void testGrowsBeyondInitialCapacity() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        byte[] big = new byte[1000];
        for (int i = 0; i < big.length; i++) {
            big[i] = (byte) i;
        }
        out.writeBytes(big);
        check(Arrays.equals(big, out.toByteArray()), "buffer must grow to hold all bytes");
    }

    static void testNullArrayThrowsNPE() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean thrown = false;
        try {
            out.writeBytes(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must throw NullPointerException");
    }

    static void testReturnedArrayIsCopy() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(new byte[]{1, 2, 3});
        byte[] first = out.toByteArray();
        first[0] = 42;
        check(Arrays.equals(new byte[]{1, 2, 3}, out.toByteArray()),
              "toByteArray must return a copy unaffected by later mutation");
    }
}
