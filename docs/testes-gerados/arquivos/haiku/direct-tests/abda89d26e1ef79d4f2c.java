/*
 * @test
 * @summary Verify ZoneOffsetTransition.getInstant returns the transition instant computed from offsetBefore
 * @run main ZoneOffsetTransitionWitupTest
 */
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {
    public static void main(String[] args) {
        ZoneOffset utc = ZoneOffset.UTC;
        ZoneOffset plus1 = ZoneOffset.ofHours(1);
        ZoneOffset plus2 = ZoneOffset.ofHours(2);
        ZoneOffset minus5 = ZoneOffset.ofHours(-5);

        LocalDateTime gap = LocalDateTime.of(2012, 3, 25, 1, 0);
        ZoneOffsetTransition gapTransition = ZoneOffsetTransition.of(gap, utc, plus1);
        Instant gapInstant = gapTransition.getInstant();
        if (gapInstant.getEpochSecond() != gap.toEpochSecond(utc)) {
            throw new RuntimeException("gap instant mismatch");
        }
        if (gapInstant.getNano() != 0) {
            throw new RuntimeException("gap instant nanos must be zero");
        }

        LocalDateTime overlap = LocalDateTime.of(2012, 10, 28, 2, 0);
        ZoneOffsetTransition overlapTransition = ZoneOffsetTransition.of(overlap, plus2, plus1);
        if (overlapTransition.getInstant().getEpochSecond() != overlap.toEpochSecond(plus2)) {
            throw new RuntimeException("overlap instant mismatch");
        }

        ZoneOffsetTransition sameBeforeA = ZoneOffsetTransition.of(overlap, plus2, plus1);
        ZoneOffsetTransition sameBeforeB = ZoneOffsetTransition.of(overlap, plus2, minus5);
        if (!sameBeforeA.getInstant().equals(sameBeforeB.getInstant())) {
            throw new RuntimeException("instant must not depend on offsetAfter");
        }

        ZoneOffsetTransition beforeOne = ZoneOffsetTransition.of(overlap, plus1, plus2);
        if (beforeOne.getInstant().equals(sameBeforeA.getInstant())) {
            throw new RuntimeException("instant must depend on offsetBefore");
        }

        ZoneOffsetTransition epoch = ZoneOffsetTransition.of(LocalDateTime.of(1970, 1, 1, 0, 0), utc, plus1);
        if (!epoch.getInstant().equals(Instant.EPOCH)) {
            throw new RuntimeException("epoch instant mismatch");
        }
    }
}
