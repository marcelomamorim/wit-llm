/*
 * @test
 * @summary Test for CyclicBarrier.isBroken() method.
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testIsBrokenReturnsCorrectValue();
    }

    public static void testIsBrokenReturnsCorrectValue() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        boolean initialBrokenState = barrier.isBroken();
        if (initialBrokenState) {
            System.out.println("Initial barrier is broken.");
        } else {
            System.out.println("Initial barrier is not broken.");
        }
        // Access the private lock field via reflection to simulate a broken state
        try {
            java.lang.reflect.Field lockField = CyclicBarrier.class.getDeclaredField("lock");
            lockField.setAccessible(true);
            ReentrantLock lock = (ReentrantLock) lockField.get(barrier);
            // Lock the lock to simulate concurrent access
            lock.lock();
            try {
                // Use reflection to set the 'generation.broken' to true
                java.lang.reflect.Field generationField = CyclicBarrier.class.getDeclaredField("generation");
                generationField.setAccessible(true);
                Object generation = generationField.get(barrier);
                java.lang.reflect.Field brokenField = generation.getClass().getDeclaredField("broken");
                brokenField.setAccessible(true);
                brokenField.setBoolean(generation, true);
            } finally {
                lock.unlock();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Reflection failed: " + e);
        }
        boolean brokenStateAfter = barrier.isBroken();
        System.out.println("Barrier broken state after reflection: " + brokenStateAfter);
        // Verify that isBroken() reflects the broken state
        if (brokenStateAfter) {
            System.out.println("Test passed: isBroken() detects broken state.");
        } else {
            System.out.println("Test failed: isBroken() does not detect broken state.");
        }
    }
}
