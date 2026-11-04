/*
 * @test
 * @summary Verifies Collator.setStrength accepts the four valid strengths and rejects other values without changing the strength
 * @run main CollatorWitupTest
 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest {

    public static void main(String[] args) {
        Collator collator = Collator.getInstance(Locale.US);

        int[] validStrengths = {
            Collator.PRIMARY,
            Collator.SECONDARY,
            Collator.TERTIARY,
            Collator.IDENTICAL
        };
        for (int strength : validStrengths) {
            collator.setStrength(strength);
            if (collator.getStrength() != strength) {
                throw new RuntimeException("Strength not updated to " + strength);
            }
        }

        collator.setStrength(Collator.TERTIARY);
        int[] invalidStrengths = {-1, 4, 100, Integer.MIN_VALUE};
        for (int invalid : invalidStrengths) {
            int before = collator.getStrength();
            boolean thrown = false;
            try {
                collator.setStrength(invalid);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("No IllegalArgumentException for " + invalid);
            }
            if (collator.getStrength() != before) {
                throw new RuntimeException("Strength changed after invalid value " + invalid);
            }
        }
    }
}
