/*
 * @test
 * @summary Tests for java.text.Collator
 * @run main CollatorWitupTest_P5ba56b94d69b
 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest_P5ba56b94d69b {

    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValues();
    }

    private static void testSetStrengthValidValues() {
        Collator collator = Collator.getInstance();
        try {
            collator.setStrength(Collator.PRIMARY);
            collator.setStrength(Collator.SECONDARY);
            collator.setStrength(Collator.TERTIARY);
            collator.setStrength(Collator.IDENTICAL);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid strength values", e);
        }
    }

    private static void testSetStrengthInvalidValues() {
        Collator collator = Collator.getInstance();
        int[] invalidValues = { -1, 4, 100 };
        for (int invalidValue : invalidValues) {
            try {
                collator.setStrength(invalidValue);
                throw new AssertionError("Expected IllegalArgumentException for value: " + invalidValue);
            } catch (IllegalArgumentException e) {
                // Expected exception
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception type for value: " + invalidValue, e);
            }
        }
    }
}
