/*
 * @test
 * @summary Teste do método nextSetBit da classe java.util.BitSet
 * @run main BitSetWitupTest_P733d11c181a5
 */

import java.util.BitSet;

public class BitSetWitupTest_P733d11c181a5 {
    public static void main(String[] args) {
        testNextSetBitWithNegativeIndex();
        testNextSetBitWithIndexBeyondWordsInUse();
        testNextSetBitWithValidIndex();
        testNextSetBitWithZeroIndex();
    }

    static void testNextSetBitWithNegativeIndex() {
        BitSet bs = new BitSet();
        try {
            bs.nextSetBit(-1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for negative index");
        }
    }

    static void testNextSetBitWithIndexBeyondWordsInUse() {
        BitSet bs = new BitSet();
        // Garantir que wordsInUse é maior que 0, adicionando bits
        bs.set(0);
        // Encontrar o índice além de wordsInUse
        int index = (bs.wordsInUse) * 64 + 10;
        int result = bs.nextSetBit(index);
        if (result == -1) {
            System.out.println("PASS: nextSetBit beyond wordsInUse returns -1");
        } else {
            System.out.println("FAIL: Expected -1 for index beyond wordsInUse, got " + result);
        }
    }

    static void testNextSetBitWithValidIndex() {
        BitSet bs = new BitSet();
        bs.set(100);
        int result = bs.nextSetBit(0);
        if (result == 100) {
            System.out.println("PASS: nextSetBit correctly finds set bit at 100");
        } else {
            System.out.println("FAIL: Expected 100, got " + result);
        }
    }

    static void testNextSetBitWithZeroIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        int result = bs.nextSetBit(0);
        if (result == 0) {
            System.out.println("PASS: nextSetBit at index 0 returns 0 when bit 0 is set");
        } else {
            System.out.println("FAIL: Expected 0, got " + result);
        }
    }
}
