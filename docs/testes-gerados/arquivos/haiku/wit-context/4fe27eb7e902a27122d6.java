/*
 * @test
 * @summary Verify BitArray.get bounds checking and bit values
 * @modules java.base/sun.security.util
 * @run main BitArrayWitupTest
 */

import sun.security.util.BitArray;

public class BitArrayWitupTest {

    public static void main(String[] args) {
        testZeroInitialized();
        testBooleanConstructorValues();
        testByteConstructorValues();
        testNegativeIndexThrows();
        testIndexEqualToLengthThrows();
        testIndexBeyondLengthThrows();
    }

    static void testZeroInitialized() {
        BitArray ba = new BitArray(8);
        for (int i = 0; i < 8; i++) {
            if (ba.get(i)) {
                throw new RuntimeException("Expected zero bit at " + i);
            }
        }
    }

    static void testBooleanConstructorValues() {
        BitArray ba = new BitArray(new boolean[] {true, false, true});
        if (!ba.get(0) || ba.get(1) || !ba.get(2)) {
            throw new RuntimeException("Unexpected bit values from boolean[] constructor");
        }
    }

    static void testByteConstructorValues() {
        BitArray ba = new BitArray(9, new byte[] {(byte) 0x80, (byte) 0x80});
        if (!ba.get(0)) {
            throw new RuntimeException("Expected bit 0 set");
        }
        if (ba.get(1)) {
            throw new RuntimeException("Expected bit 1 clear");
        }
        if (!ba.get(8)) {
            throw new RuntimeException("Expected bit 8 set");
        }
        expectAIOOBE(ba, 9);
    }

    static void testNegativeIndexThrows() {
        BitArray ba = new BitArray(4);
        expectAIOOBE(ba, -1);
    }

    static void testIndexEqualToLengthThrows() {
        BitArray ba = new BitArray(3);
        ba.get(2);
        expectAIOOBE(ba, 3);
    }

    static void testIndexBeyondLengthThrows() {
        BitArray ba = new BitArray(5);
        expectAIOOBE(ba, 100);
    }

    static void expectAIOOBE(BitArray ba, int idx) {
        try {
            ba.get(idx);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index " + idx);
    }
}
