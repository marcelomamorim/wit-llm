/*
 * @test
 * @summary Checks ThreadLocalRandom long stream spliterator tryAdvance behavior: bounded values, consumer count, exhaustion and null consumer handling
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }

    public static void main(String[] args) {
        testBoundedElementsAndExhaustion();
        testNullConsumerThrowsNPE();
    }

    static void testBoundedElementsAndExhaustion() {
        Spliterator.OfLong s = ThreadLocalRandom.current().longs(3, 10L, 20L).spliterator();
        final int[] count = {0};
        for (int k = 0; k < 3; k++) {
            boolean advanced = s.tryAdvance((long v) -> {
                check(v >= 10L && v < 20L);
                count[0]++;
            });
            check(advanced);
        }
        check(count[0] == 3);

        final int[] extra = {0};
        boolean again = s.tryAdvance((long v) -> extra[0]++);
        check(!again);
        check(extra[0] == 0);
        check(count[0] == 3);
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong s = ThreadLocalRandom.current().longs(1, 0L, 1L).spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown);
    }
}
