/*
 * @test
 * @summary Verify argument validation and basic results of ZoneOffsetTransition.of
 * @run main ZoneOffsetTransitionWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectException(Class<? extends Throwable> expected, Action action, String label) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException(label + ": unexpected exception " + t, t);
        }
        throw new RuntimeException(label + ": expected " + expected.getName() + " was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        LocalDateTime transition = LocalDateTime.of(2012, 3, 25, 2, 0);
        ZoneOffset before = ZoneOffset.ofHours(0);
        ZoneOffset after = ZoneOffset.ofHours(1);

        expectException(NullPointerException.class,
                () -> ZoneOffsetTransition.of(null, before, after), "null transition");
        expectException(NullPointerException.class,
                () -> ZoneOffsetTransition.of(transition, null, after), "null offsetBefore");
        expectException(NullPointerException.class,
                () -> ZoneOffsetTransition.of(transition, before, null), "null offsetAfter");

        expectException(IllegalArgumentException.class,
                () -> ZoneOffsetTransition.of(transition, before, ZoneOffset.ofHours(0)), "equal offsets");

        expectException(IllegalArgumentException.class,
                () -> ZoneOffsetTransition.of(transition.withNano(1), before, after), "nano not zero");

        ZoneOffsetTransition gap = ZoneOffsetTransition.of(transition, before, after);
        check(gap != null, "gap transition must not be null");
        check(gap.getOffsetBefore().equals(before), "gap offsetBefore mismatch");
        check(gap.getOffsetAfter().equals(after), "gap offsetAfter mismatch");

        ZoneOffsetTransition overlap = ZoneOffsetTransition.of(transition, after, before);
        check(overlap != null, "overlap transition must not be null");
        check(overlap.getOffsetBefore().equals(after), "overlap offsetBefore mismatch");
        check(overlap.getOffsetAfter().equals(before), "overlap offsetAfter mismatch");
    }
}
