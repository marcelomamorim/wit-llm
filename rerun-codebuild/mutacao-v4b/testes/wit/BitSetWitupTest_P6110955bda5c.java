/* @test
   @summary Testa o método get(int, int) de java.util.BitSet para comportamentos observáveis.
   @run main BitSetWitupTest_P6110955bda5c
 */
import java.util.BitSet;
public class BitSetWitupTest_P6110955bda5c {
    public static void main(String[] args) {
        testGetWithValidRange();
        testGetWithFromIndexNegative();
        testGetWithToIndexNegative();
        testGetWithFromIndexGreaterThanToIndex();
        testGetWithToIndexExceedsLength();
    }

    static void testGetWithValidRange() {
        BitSet bs = new BitSet();
        bs.set(2);
        bs.set(4);
        BitSet result = bs.get(1, 5);
        if (result == null) throw new AssertionError("Result should not be null");
        if (result.length() != 3) throw new AssertionError("Expected length 3");
        if (!result.get(1)) throw new AssertionError("Bit 1 should be set");
        if (!result.get(2)) throw new AssertionError("Bit 2 should be set");
        if (result.get(0)) throw new AssertionError("Bit 0 should not be set");
    }

    static void testGetWithFromIndexNegative() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.get(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithToIndexNegative() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.get(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        bs.set(0);
        try {
            bs.get(3, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithToIndexExceedsLength() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(3);
        BitSet result = bs.get(0, 10); // toIndex exceeds length
        if (result == null) throw new AssertionError("Result should not be null");
        if (result.length() != 4) throw new AssertionError("Expected length 4");
        if (!result.get(1)) throw new AssertionError("Bit 1 should be set");
        if (!result.get(3)) throw new AssertionError("Bit 3 should be set");
    }
}
