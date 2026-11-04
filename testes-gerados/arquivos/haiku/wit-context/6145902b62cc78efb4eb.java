/*
 * @test
 * @summary WITUP-derived checks for StackWalker.getInstance(Set, int) exception paths and basic usability
 * @run main StackWalkerWitupTest
 */

import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        expectIAE(EnumSet.noneOf(StackWalker.Option.class), 0);
        expectIAE(EnumSet.noneOf(StackWalker.Option.class), -1);
        expectIAE(null, 0);
        expectIAE(null, -5);
        expectNPE(null, 1);
        expectNPE(null, 10);

        StackWalker walker = StackWalker.getInstance(
                EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE), 1);
        if (walker == null) {
            throw new AssertionError("getInstance returned null");
        }
        long count = walker.walk(s -> s.count());
        if (count <= 0) {
            throw new AssertionError("expected at least one stack frame, got " + count);
        }
    }

    static void expectIAE(Set<StackWalker.Option> options, int depth) {
        try {
            StackWalker.getInstance(options, depth);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for depth " + depth);
    }

    static void expectNPE(Set<StackWalker.Option> options, int depth) {
        try {
            StackWalker.getInstance(options, depth);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for depth " + depth);
    }
}
