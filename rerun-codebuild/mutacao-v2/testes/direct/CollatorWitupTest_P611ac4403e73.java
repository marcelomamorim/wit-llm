/*
 * @test
 * @summary Test Collator.setStrength with valid and invalid values
 * @run main CollatorWitupTest_P611ac4403e73
 */

import java.text.Collator;

public class CollatorWitupTest_P611ac4403e73 {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();

        // Test valid strengths
        collator.setStrength(Collator.PRIMARY);
        collator.setStrength(Collator.SECONDARY);
        collator.setStrength(Collator.TERTIARY);
        collator.setStrength(Collator.IDENTICAL);

        // Test invalid strength values
        int[] invalidValues = { -1, 0, 4, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int val : invalidValues) {
            try {
                collator.setStrength(val);
                throw new RuntimeException("Expected IllegalArgumentException for strength: " + val);
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        }
    }
}
