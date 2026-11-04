/*
 * @test
 * @summary Test java.util.Random.RandomIntsSpliterator.forEachRemaining(IntConsumer) for NPE on null consumer
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.function.IntConsumer;

public class RandomWitupTest {
    public static void main(String[] args) {
        // Create a Random instance with a fixed seed for determinism
        Random random = new Random(12345L);

        // Create a RandomIntsSpliterator instance via Random.ints() to access forEachRemaining
        // Since RandomIntsSpliterator is package-private and inner, we use Random.ints() stream
        // and get its spliterator, then cast to Object and reflect behavior via forEachRemaining

        // We test the forEachRemaining method indirectly by calling ints().spliterator().forEachRemaining
        // with a valid IntConsumer and with null to check for NullPointerException

        // Test 1: forEachRemaining with a valid IntConsumer
        IntConsumer consumer = new IntConsumer() {
            int count = 0;
            public void accept(int value) {
                count++;
                if (count > 10) throw new RuntimeException("Too many calls");
            }
        };

        // Obtain spliterator from Random.ints()
        java.util.Spliterator.OfInt spliterator = random.ints(10).spliterator();

        // Call forEachRemaining with valid consumer - should not throw
        spliterator.forEachRemaining(consumer);

        // Test 2: forEachRemaining with null consumer - should throw NullPointerException
        boolean npeThrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }

        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        }

        System.out.println("Test passed: forEachRemaining throws NullPointerException on null consumer and works with valid consumer.");
    }
}
