/*
 * @test
 * @summary Verify BitArray public constructors and get() bounds checking and bit ordering
 * @modules java.base/sun.security.util
 * @run main BitArrayWitupTest
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testZeroLength();
        testZeroInitialized();
        testBoundsOnGet();
        testBooleanConstructor();
        testByteConstructorBitOrder();
        testByteConstructorLongerLength();
        testNegativeLengthRejected();
        testByteArrayTooShort();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static boolean getThrowsAIOOBE(BitArray b, int idx) {
        try {
            b.get(idx);
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    static void testZeroLength() {
        BitArray b = new BitArray(0);
        check(getThrowsAIOOBE(b, 0), "index 0 on empty array must throw");
        check(getThrowsAIOOBE(b, -1), "index -1 on empty array must throw");
    }

    static void testZeroInitialized() {
        BitArray b = new BitArray(10);
        for (int i = 0; i < 10; i++) {
            check(!b.get(i), "new BitArray must be initialized to zeros");
        }
        check(getThrowsAIOOBE(b, 10), "index equal to length must throw");
    }

    static void testBoundsOnGet() {
        BitArray b = new BitArray(5);
        check(getThrowsAIOOBE(b, -1), "negative index must throw");
        check(getThrowsAIOOBE(b, 5), "index equal to length must throw");
        check(getThrowsAIOOBE(b, 100), "large index must throw");
    }

    static void testBooleanConstructor() {
        boolean[] bits = new boolean[9];
        bits[0] = true;
        bits[3] = true;
        bits[8] = true;
        BitArray b = new BitArray(bits);
        for (int i = 0; i < bits.length; i++) {
            check(b.get(i) == bits[i], "boolean constructor must preserve bits");
        }
        check(getThrowsAIOOBE(b, 9), "index past boolean array length must throw");
    }

    static void testByteConstructorBitOrder() {
        BitArray msb = new BitArray(8, new byte[] {(byte) 0x80});
        check(msb.get(0), "MSB of first byte must be index 0");
        for (int i = 1; i < 8; i++) {
            check(!msb.get(i), "other bits must be clear");
        }

        BitArray lsb = new BitArray(8, new byte[] {(byte) 0x01});
        check(!lsb.get(0), "index 0 must be clear for 0x01");
        check(lsb.get(7), "LSB of first byte must be index 7");
    }

    static void testByteConstructorLongerLength() {
        BitArray b = new BitArray(9, new byte[] {(byte) 0x00, (byte) 0x80});
        check(b.get(8), "bit 8 must come from second byte MSB");
        for (int i = 0; i < 8; i++) {
            check(!b.get(i), "first byte bits must be clear");
        }
        check(getThrowsAIOOBE(b, 9), "index past length must throw");

        BitArray three = new BitArray(3, new byte[] {(byte) 0xFF});
        for (int i = 0; i < 3; i++) {
            check(three.get(i), "bits within length must be set");
        }
        check(getThrowsAIOOBE(three, 3), "index equal to length must throw");
    }

    static void testNegativeLengthRejected() {
        boolean thrown = false;
        try {
            new BitArray(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative length must throw IllegalArgumentException");

        thrown = false;
        try {
            new BitArray(-1, new byte[1]);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative length with byte array must throw IllegalArgumentException");
    }

    static void testByteArrayTooShort() {
        boolean thrown = false;
        try {
            new BitArray(9, new byte[1]);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "byte array too short must throw IllegalArgumentException");
    }
}
