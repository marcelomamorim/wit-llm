/* @test    @summary Test Collator.setStrength with valid and invalid values    @run main CollatorWitupTest_P297deacf3cbb */

import java.text.Collator;

public class CollatorWitupTest_P297deacf3cbb {

    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValue();
    }

    private static void testSetStrengthValidValues() {
        Collator collator = Collator.getInstance();
        try {
            collator.setStrength(Collator.PRIMARY);
            collator.setStrength(Collator.SECONDARY);
            collator.setStrength(Collator.TERTIARY);
            collator.setStrength(Collator.IDENTICAL);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid strength values", e);
        }
    }

    private static void testSetStrengthInvalidValue() {
        Collator collator = Collator.getInstance();
        int invalidValue = -1;
        boolean exceptionThrown = false;
        try {
            collator.setStrength(invalidValue);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for invalid strength value", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid strength value")
        }
    }
}
