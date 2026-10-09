/* @test
   @summary Tests the setDecomposition method of Collator for valid and invalid inputs.
   @run main CollatorWitupTest_Pbe39f2474193
 */
import java.text.Collator;
public class CollatorWitupTest_Pbe39f2474193 {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();
        // Test with valid modes
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for NO_DECOMPOSITION", e);
        }
        try {
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for CANONICAL_DECOMPOSITION", e);
        }
        try {
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for FULL_DECOMPOSITION", e);
        }
        // Test with invalid mode
        boolean exceptionThrown = false;
        try {
            collator.setDecomposition(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid mode");
        }
        // Test with another invalid mode
        exceptionThrown = false;
        try {
            collator.setDecomposition(999);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid mode");
        }
    }
}
