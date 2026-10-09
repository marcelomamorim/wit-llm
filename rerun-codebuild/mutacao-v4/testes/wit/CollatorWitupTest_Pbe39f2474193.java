/* @test
   @summary Tests for Collator.setDecomposition method.
   @run main CollatorWitupTest_Pbe39f2474193
 */
import java.text.Collator;
public class CollatorWitupTest_Pbe39f2474193 {
    public static void main(String[] args) {
        testSetDecompositionValidModes();
        testSetDecompositionInvalidMode();
    }
    private static void testSetDecompositionValidModes() {
        Collator collator = Collator.getInstance();
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Valid modes should not throw", e);
        }
    }
    private static void testSetDecompositionInvalidMode() {
        Collator collator = Collator.getInstance();
        int invalidMode = -1;
        try {
            collator.setDecomposition(invalidMode);
            throw new AssertionError("Expected IllegalArgumentException for mode: " + invalidMode);
        } catch (IllegalArgumentException e) {
            // expected
        }
        invalidMode = 3;
        try {
            collator.setDecomposition(invalidMode);
            throw new AssertionError("Expected IllegalArgumentException for mode: " + invalidMode);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
