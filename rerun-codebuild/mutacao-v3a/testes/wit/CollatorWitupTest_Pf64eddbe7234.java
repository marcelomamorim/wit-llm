/* @test    @summary Tests for java.text.Collator.setStrength method behavior.    @run main CollatorWitupTest_Pf64eddbe7234 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest_Pf64eddbe7234 {

    public static void main(String[] args) {
        testSetStrengthWithValidValues();
        testSetStrengthWithInvalidValues();
    }

    public static void testSetStrengthWithValidValues() {
        Collator collator = Collator.getInstance(Locale.US);
        try {
            // Valid strengths: PRIMARY, SECONDARY, TERTIARY, IDENTICAL
            collator.setStrength(Collator.PRIMARY);
            collator.setStrength(Collator.SECONDARY);
            collator.setStrength(Collator.TERTIARY);
            collator.setStrength(Collator.IDENTICAL);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid strength values", e);
        }
    }

    public static void testSetStrengthWithInvalidValues() {
        Collator collator = Collator.getInstance(Locale.US);
        int[] invalidValues = { -1, 4, 999 };
        for (int invalidValue : invalidValues) {
            try {
                collator.setStrength(invalidValue);
                throw new AssertionError("Expected IllegalArgumentException for value: " + invalidValue);
            } catch (IllegalArgumentException e) {
                // Expected exception, test passes for this case
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for value: " + invalidValue, e);
            }
        }
    }
}
