/* @test    @summary Test Collator.setStrength with valid and invalid values    @run main CollatorWitupTest_P5ba56b94d69b */

import java.text.Collator;
import static java.text.Collator.*;

public class CollatorWitupTest_P5ba56b94d69b {

    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValue();
    }

    static void testSetStrengthValidValues() {
        Collator collator = Collator.getInstance();
        try {
            collator.setStrength(PRIMARY);
            assert collator != null : "Collator should not be null after setting PRIMARY";
            collator.setStrength(SECONDARY);
            assert collator != null : "Collator should not be null after setting SECONDARY";
            collator.setStrength(TERTIARY);
            assert collator != null : "Collator should not be null after setting TERTIARY";
            collator.setStrength(IDENTICAL);
            assert collator != null : "Collator should not be null after setting IDENTICAL";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid strength values", e);
        }
    }

    static void testSetStrengthInvalidValue() {
        Collator collator = Collator.getInstance();
        boolean exceptionThrown = false;
        try {
            collator.setStrength(999); // invalid value
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid strength value";
    }
}
