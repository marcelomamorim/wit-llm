/*
 * @test
 * @summary Verify ThreadLocalRandom int spliterator tryAdvance null guard and public advance behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        Spliterator.OfInt sp = ThreadLocalRandom.current().ints(2).spliterator();
        try {
            sp.tryAdvance((IntConsumer) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        int[] count = new int[1];
        IntConsumer counter = v -> count[0]++;
        if (!sp.tryAdvance(counter)) {
            throw new RuntimeException("first tryAdvance should return true");
        }
        if (!sp.tryAdvance(counter)) {
            throw new RuntimeException("second tryAdvance should return true");
        }
        if (sp.tryAdvance(counter)) {
            throw new RuntimeException("tryAdvance past fence should return false");
        }
        if (count[0] != 2) {
            throw new RuntimeException("expected 2 consumed elements but got " + count[0]);
        }
    }
}
