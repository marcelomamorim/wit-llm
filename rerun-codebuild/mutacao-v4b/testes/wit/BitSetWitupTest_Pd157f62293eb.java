/* @test
   @summary Testa o método clear(int, int) de java.util.BitSet para comportamentos observáveis.
   @run main BitSetWitupTest_Pd157f62293eb
 */
import java.util.BitSet;
public class BitSetWitupTest_Pd157f62293eb {
    public static void main(String[] args) {
        testClearWithInvalidRange();
        testClearWithNegativeFromIndex();
        testClearWithFromIndexGreaterThanToIndex();
    }

    private static void testClearWithInvalidRange() {
        BitSet bs = new BitSet();
        try {
            bs.clear(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative toIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testClearWithNegativeFromIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(-5, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testClearWithFromIndexGreaterThanToIndex() {
        BitSet bs = new BitSet();
        try {
            bs.clear(10, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
