/* @test    @summary Test for BitSet.nextSetBit(int) method to verify behavior with valid and invalid inputs    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNextSetBitWithNegativeIndex();
        testNextSetBitWithIndexBeyondWordsInUse();
        testNextSetBitWithValidSetBit();
        testNextSetBitWithNoSetBits();
    }

    private static void testNextSetBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testNextSetBitWithIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        // No bits set, index 1000 beyond current size
        int result = bs.nextSetBit(1000);
        if (result == -1) {
            System.out.println("PASS: nextSetBit(1000) returned -1 as expected when no bits are set");
        } else {
            System.out.println("FAIL: nextSetBit(1000) returned " + result + " instead of -1");
        }
    }

    private static void testNextSetBitWithValidSetBit() {
        BitSet bs = new BitSet();
        bs.set(10);
        bs.set(20);
        int result = bs.nextSetBit(0);
        if (result == 10) {
            System.out.println("PASS: nextSetBit(0) correctly returned 10");
        } else {
            System.out.println("FAIL: nextSetBit(0) returned " + result + " instead of 10");
        }
        result = bs.nextSetBit(11);
        if (result == 20) {
            System.out.println("PASS: nextSetBit(11) correctly returned 20");
        } else {
            System.out.println("FAIL: nextSetBit(11) returned " + result + " instead of 20");
        }
    }

    private static void testNextSetBitWithNoSetBits() {
        BitSet bs = new BitSet();
        int result = bs.nextSetBit(0);
        if (result == -1) {
            System.out.println("PASS: nextSetBit(0) returned -1 when no bits are set");
        } else {
            System.out.println("FAIL: nextSetBit(0) returned " + result + " instead of -1");
        }
    }
}
