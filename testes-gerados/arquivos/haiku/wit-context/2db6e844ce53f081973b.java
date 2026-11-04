/*
 * @test
 * @summary Verify Collator.setDecomposition accepts valid modes and rejects invalid ones without changing the current mode
 * @run main CollatorWitupTest
 */

import java.text.Collator;
import java.util.Locale;

public class CollatorWitupTest {

    public static void main(String[] args) {
        Collator c = Collator.getInstance(Locale.US);

        c.setDecomposition(Collator.NO_DECOMPOSITION);
        check(c.getDecomposition() == Collator.NO_DECOMPOSITION, "NO_DECOMPOSITION not applied");

        c.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        check(c.getDecomposition() == Collator.CANONICAL_DECOMPOSITION, "CANONICAL_DECOMPOSITION not applied");

        c.setDecomposition(Collator.FULL_DECOMPOSITION);
        check(c.getDecomposition() == Collator.FULL_DECOMPOSITION, "FULL_DECOMPOSITION not applied");

        c.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        assertIllegalArgument(c, -1);
        assertIllegalArgument(c, 3);
        assertIllegalArgument(c, Integer.MIN_VALUE);
        assertIllegalArgument(c, Integer.MAX_VALUE);
        check(c.getDecomposition() == Collator.CANONICAL_DECOMPOSITION,
              "mode changed after rejected value");
    }

    static void assertIllegalArgument(Collator c, int mode) {
        try {
            c.setDecomposition(mode);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for mode " + mode);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
