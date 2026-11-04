/*
 * @test
 * @summary Test Collator.setStrength(int) for valid and invalid inputs
 * @run main CollatorWitupTest
 */

import java.text.Collator;

public class CollatorWitupTest {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();

        // Test valid strengths
        collator.setStrength(Collator.PRIMARY);
        collator.setStrength(Collator.SECONDARY);
        collator.setStrength(Collator.TERTIARY);
        collator.setStrength(Collator.IDENTICAL);

        // Test invalid strengths - expect IllegalArgumentException
        int[] invalidStrengths = { -1, 4, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int s : invalidStrengths) {
            boolean threw = false;
            try {
                collator.setStrength(s);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Expected IllegalArgumentException for strength: " + s);
            }
        }

        System.out.println("Collator.setStrength tests passed.");
    }
}
