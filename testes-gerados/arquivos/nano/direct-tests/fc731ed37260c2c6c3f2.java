/* @test    @summary Test BitSet constructor with positive, zero, and negative sizes    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testConstructorPositiveSize();
        testConstructorZeroSize();
        testConstructorNegativeSize();
    }

    static void testConstructorPositiveSize() {
        int size = 10;
        BitSet bs = new BitSet(size);
        boolean allClear = true;
        for (int i = 0; i < size; i++) {
            if (bs.get(i)) {
                allClear = false;
                break;
            }
        }
        if (!allClear) {
            throw new AssertionError("Expected all bits to be false for size " + size);
        }
    }

    static void testConstructorZeroSize() {
        int size = 0;
        BitSet bs = new BitSet(size);
        if (bs.length() != 0) {
            throw new AssertionError("Expected length 0 for zero size, but got " + bs.length());
        }
    }

    static void testConstructorNegativeSize() {
        boolean exceptionThrown = false;
        try {
            new BitSet(-1);
        } catch (NegativeArraySizeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NegativeArraySizeException for negative size");
        }
    }
}
