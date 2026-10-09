/* @test    @summary Test Collator.setStrength() for valid and invalid inputs    @run main CollatorWitupTest_Pf64eddbe7234 */

import java.text.Collator;

public class CollatorWitupTest_Pf64eddbe7234 {

    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValue();
    }

    private static void testSetStrengthValidValues() {
        Collator collator = Collator.getInstance();
        try {
            collator.setStrength(Collator.PRIMARY);
            assert collator != null : "Collator instance should not be null after setting PRIMARY";
            collator.setStrength(Collator.SECONDARY);
            assert collator != null : "Collator instance should not be null after setting SECONDARY";
            collator.setStrength(Collator.TERTIARY);
            assert collator != null : "Collator instance should not be null after setting TERTIARY";
            collator.setStrength(Collator.IDENTICAL);
            assert collator != null : "Collator instance should not be null after setting IDENTICAL";
        } catch (Exception e) {
            throw new AssertionError("Setting valid strength values threw an exception", e);
        }
    }

    private static void testSetStrengthInvalidValue() {
        Collator collator = Collator.getInstance();
        int invalidStrength = -1;
        boolean exceptionThrown = false;
        try {
            collator.setStrength(invalidStrength);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid strength value";
    }
}
