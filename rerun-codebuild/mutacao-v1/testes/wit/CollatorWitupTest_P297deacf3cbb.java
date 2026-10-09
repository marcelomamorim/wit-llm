/* @test    @summary Test setting valid and invalid strength values in Collator    @run main CollatorWitupTest_P297deacf3cbb */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest_P297deacf3cbb {

    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValue();
    }

    private static void testSetStrengthValidValues() {
        try {
            Collator collator = Collator.getInstance(Locale.US);
            // Valid strengths: PRIMARY, SECONDARY, TERTIARY, IDENTICAL
            collator.setStrength(Collator.PRIMARY);
            collator.setStrength(Collator.SECONDARY);
            collator.setStrength(Collator.TERTIARY);
            collator.setStrength(Collator.IDENTICAL);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid strengths: " + e);
        }
    }

    private static void testSetStrengthInvalidValue() {
        try {
            Collator collator = Collator.getInstance(Locale.US);
            // Invalid strength: 999 (not defined)
            collator.setStrength(999);
            throw new AssertionError("Expected IllegalArgumentException for invalid strength, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
