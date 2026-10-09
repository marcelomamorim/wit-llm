/*
 * @test
 * @summary Testa o método flip de java.util.BitSet para condições excepcionais
 * @run main BitSetWitupTest_P0eabc4460916
 */

import java.util.BitSet;

public class BitSetWitupTest_P0eabc4460916 {

    public static void main(String[] args) {
        testFlipWithInvalidRange();
        testFlipWithNegativeFromIndex();
        testFlipWithNegativeToIndex();
    }

    private static void testFlipWithInvalidRange() {
        BitSet bs = new BitSet(10);
        int fromIndex = 5;
        int toIndex = 4; // fromIndex > toIndex
        boolean exceptionThrown = false;
        try {
            bs.flip(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        }
    }

    private static void testFlipWithNegativeFromIndex() {
        BitSet bs = new BitSet(10);
        int fromIndex = -1;
        int toIndex = 5;
        boolean exceptionThrown = false;
        try {
            bs.flip(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        }
    }

    private static void testFlipWithNegativeToIndex() {
        BitSet bs = new BitSet(10);
        int fromIndex = 0;
        int toIndex = -1;
        boolean exceptionThrown = false;
        try {
            bs.flip(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative toIndex");
        }
    }
}
