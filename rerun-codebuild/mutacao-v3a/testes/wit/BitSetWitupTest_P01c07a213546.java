/*
 * @test
 * @summary Tests for BitSet.get(int, int) method
 * @run main BitSetWitupTest_P01c07a213546
 */

import java.util.BitSet;

public class BitSetWitupTest_P01c07a213546 {

    public static void main(String[] args) {
        testGet_NormalCases();
        testGet_IndexOutOfBounds();
    }

    static void testGet_NormalCases() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(4);
        bs.set(10);
        // Range within the set bits
        BitSet result = bs.get(2, 11);
        assert result.get(0): "Expected bit 2 to be set";
        assert result.get(2): "Expected bit 4 to be set";
        assert result.get(8): "Expected bit 10 to be set";
        // Range outside the set bits should be empty
        result = bs.get(0, 2);
        assert result.isEmpty() : "Expected empty BitSet";
        // Range equal to length
        result = bs.get(0, bs.length());
        assert result.get(2): "Expected bit 2 to be present";
        assert result.get(4): "Expected bit 4 to be present";
        assert result.get(10): "Expected bit 10 to be present";
    }

    static void testGet_IndexOutOfBounds() {
        BitSet bs = new BitSet();
        bs.set(1);
        // fromIndex < 0 should throw IndexOutOfBoundsException
        try {
            bs.get(-1, 5);
            assert false : "Expected IndexOutOfBoundsException for fromIndex < 0";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // toIndex < 0 should throw IndexOutOfBoundsException
        try {
            bs.get(0, -1);
            assert false : "Expected IndexOutOfBoundsException for toIndex < 0";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // fromIndex > toIndex should throw IndexOutOfBoundsException
        try {
            bs.get(5, 2);
            assert false : "Expected IndexOutOfBoundsException for fromIndex > toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // fromIndex >= length should result in empty BitSet (per code logic)
        try {
            BitSet result = bs.get(100, 105);
            assert result.isEmpty() : "Expected empty BitSet for range beyond length";
        } catch (IndexOutOfBoundsException e) {
            assert false : "Did not expect exception for range beyond length";
        }
    }
}
