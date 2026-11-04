/*
 * @test
 * @summary Teste do método previousSetBit para verificar comportamento com índices inválidos.
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testPreviousSetBitNegativeIndex();
        testPreviousSetBitIndexBeyondSize();
        testPreviousSetBitValidIndex();
    }

    private static void testPreviousSetBitNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            int result = bs.previousSetBit(-2);
            System.out.println("testPreviousSetBitNegativeIndex: FAIL - Expected exception, got " + result);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testPreviousSetBitNegativeIndex: PASS");
        } catch (Throwable t) {
            System.out.println("testPreviousSetBitNegativeIndex: FAIL - Unexpected exception: " + t);
        }
    }

    private static void testPreviousSetBitIndexBeyondSize() {
        BitSet bs = new BitSet();
        // Set some bits to ensure the BitSet is not empty
        bs.set(10);
        int indexBeyond = 1000;
        int result = bs.previousSetBit(indexBeyond);
        if (result >= 0) {
            System.out.println("testPreviousSetBitIndexBeyondSize: PASS - Result: " + result);
        } else {
            System.out.println("testPreviousSetBitIndexBeyondSize: FAIL - Expected >= 0, got " + result);
        }
    }

    private static void testPreviousSetBitValidIndex() {
        BitSet bs = new BitSet();
        bs.set(5);
        bs.set(10);
        int index = 10;
        int result = bs.previousSetBit(index);
        if (result == 5) {
            System.out.println("testPreviousSetBitValidIndex: PASS");
        } else {
            System.out.println("testPreviousSetBitValidIndex: FAIL - Expected 5, got " + result);
        }
    }
}
