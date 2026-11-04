/*
 * @test
 * @summary Witup-derived checks for BitSet.set(int, boolean) observable behavior and negative index handling
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetTrueSetsBit();
        testSetFalseClearsBit();
        testSetFalseOnEmptyDoesNotGrow();
        testSetTrueNegativeIndexThrows();
        testSetFalseNegativeIndexThrows();
        testSetTrueMinValueThrows();
        testSetFalseMinValueThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetTrueSetsBit() {
        BitSet b = new BitSet();
        b.set(5, true);
        check(b.get(5), "set(5,true) must set bit 5");
        check(b.cardinality() == 1, "cardinality must be 1 after set(5,true)");
        check(b.length() == 6, "length must be 6 after set(5,true)");
    }

    static void testSetFalseClearsBit() {
        BitSet b = new BitSet();
        b.set(5);
        b.set(5, false);
        check(!b.get(5), "set(5,false) must clear bit 5");
        check(b.cardinality() == 0, "cardinality must be 0 after set(5,false)");
    }

    static void testSetFalseOnEmptyDoesNotGrow() {
        BitSet b = new BitSet();
        b.set(7, false);
        check(b.isEmpty(), "set(7,false) on empty set must leave it empty");
        check(b.length() == 0, "length must remain 0 after set(7,false) on empty set");
    }

    static void testSetTrueNegativeIndexThrows() {
        final BitSet b = new BitSet();
        boolean thrown = false;
        try {
            b.set(-1, true);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(-1,true) must throw IndexOutOfBoundsException");
        check(b.isEmpty(), "state must be unchanged after rejected set(-1,true)");
    }

    static void testSetFalseNegativeIndexThrows() {
        final BitSet b = new BitSet();
        boolean thrown = false;
        try {
            b.set(-1, false);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(-1,false) must throw IndexOutOfBoundsException");
        check(b.isEmpty(), "state must be unchanged after rejected set(-1,false)");
    }

    static void testSetTrueMinValueThrows() {
        final BitSet b = new BitSet();
        boolean thrown = false;
        try {
            b.set(Integer.MIN_VALUE, true);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(Integer.MIN_VALUE,true) must throw IndexOutOfBoundsException");
    }

    static void testSetFalseMinValueThrows() {
        final BitSet b = new BitSet();
        boolean thrown = false;
        try {
            b.set(Integer.MIN_VALUE, false);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "set(Integer.MIN_VALUE,false) must throw IndexOutOfBoundsException");
    }
}
