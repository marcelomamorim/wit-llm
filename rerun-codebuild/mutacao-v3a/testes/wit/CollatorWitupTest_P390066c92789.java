/* @test    @summary Test for Collator.setDecomposition handling valid and invalid inputs    @run main CollatorWitupTest_P390066c92789 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest_P390066c92789 {

    public static void main(String[] args) {
        testSetDecompositionWithValidValues();
        testSetDecompositionWithInvalidValue();
    }

    private static void testSetDecompositionWithValidValues() {
        Collator collator = Collator.getInstance(Locale.US);
        try {
            collator.setDecomposition(Collator.NO_DECOMPOSITION);
            collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
            System.out.println("Valid decomposition modes accepted.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid modes", e);
        }
    }

    private static void testSetDecompositionWithInvalidValue() {
        Collator collator = Collator.getInstance(Locale.US);
        int invalidMode = -1;
        try {
            collator.setDecomposition(invalidMode);
            throw new AssertionError("Expected IllegalArgumentException for invalid mode");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for invalid mode.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
