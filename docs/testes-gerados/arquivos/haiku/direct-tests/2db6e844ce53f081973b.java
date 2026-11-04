/*
 * @test
 * @summary Tests Collator.setDecomposition accepted and rejected modes
 * @run main CollatorWitupTest
 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest {
    public static void main(String[] args) {
        Collator c = Collator.getInstance(Locale.US);

        int[] valid = {
            Collator.NO_DECOMPOSITION,
            Collator.CANONICAL_DECOMPOSITION,
            Collator.FULL_DECOMPOSITION
        };
        for (int mode : valid) {
            c.setDecomposition(mode);
            if (c.getDecomposition() != mode) {
                throw new RuntimeException("Decomposition mode not stored: " + mode);
            }
        }

        int[] invalid = { -1, 3, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int mode : invalid) {
            boolean thrown = false;
            try {
                c.setDecomposition(mode);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("No IllegalArgumentException for mode: " + mode);
            }
        }

        c.setDecomposition(Collator.FULL_DECOMPOSITION);
        try {
            c.setDecomposition(-5);
        } catch (IllegalArgumentException expected) {
            // expected
        }
        if (c.getDecomposition() != Collator.FULL_DECOMPOSITION) {
            throw new RuntimeException("Mode changed after rejected value");
        }
    }
}
