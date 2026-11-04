/*
 * @test
 * @summary Verifies that Collator.setStrength accepts the four valid levels and rejects other values without changing the current strength
 * @run main CollatorWitupTest
 */
import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest {

    public static void main(String[] args) {
        Collator collator = Collator.getInstance(Locale.US);

        int[] validLevels = {
            Collator.PRIMARY,
            Collator.SECONDARY,
            Collator.TERTIARY,
            Collator.IDENTICAL
        };
        for (int level : validLevels) {
            collator.setStrength(level);
            if (collator.getStrength() != level) {
                throw new RuntimeException("Strength not set to " + level);
            }
        }

        int[] invalidLevels = {
            -1, 4, 5, 100, Integer.MIN_VALUE, Integer.MAX_VALUE
        };
        for (int invalid : invalidLevels) {
            collator.setStrength(Collator.TERTIARY);
            try {
                collator.setStrength(invalid);
                throw new RuntimeException("IllegalArgumentException not thrown for " + invalid);
            } catch (IllegalArgumentException expected) {
                if (collator.getStrength() != Collator.TERTIARY) {
                    throw new RuntimeException("Strength changed after rejected value " + invalid);
                }
            }
        }
    }
}
