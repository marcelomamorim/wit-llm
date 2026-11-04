/*
 * @test
 * @summary Verifies forEachRemaining(IntConsumer) on the int-array spliterator returned by Spliterators: null action throws NPE and normal traversal is observable.
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionDoesNotConsumeElements();
        testForEachRemainingTraversesAllElements();
        System.out.println("PASS");
    }

    private static void testNullActionThrowsNpe() {
        int[] data = {3, 1, 4, 1, 5};
        Spliterator.OfInt s = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    private static void testNullActionDoesNotConsumeElements() {
        int[] data = {3, 1, 4, 1, 5};
        Spliterator.OfInt s = Spliterators.spliterator(data, Spliterator.ORDERED);
        try {
            s.forEachRemaining((IntConsumer) null);
        } catch (NullPointerException e) {
            // expected
        }
        StringBuilder sb = new StringBuilder();
        s.forEachRemaining((IntConsumer) x -> sb.append(x).append(','));
        if (!"3,1,4,1,5,".equals(sb.toString())) {
            throw new RuntimeException("Elements consumed after failed call: " + sb);
        }
    }

    private static void testForEachRemainingTraversesAllElements() {
        int[] data = {3, 1, 4, 1, 5};
        Spliterator.OfInt s = Spliterators.spliterator(data, Spliterator.ORDERED);
        StringBuilder sb = new StringBuilder();
        s.forEachRemaining((IntConsumer) x -> sb.append(x).append(','));
        if (!"3,1,4,1,5,".equals(sb.toString())) {
            throw new RuntimeException("Unexpected traversal: " + sb);
        }
        boolean advanced = s.tryAdvance((IntConsumer) x -> {
            throw new RuntimeException("No element expected after traversal");
        });
        if (advanced) {
            throw new RuntimeException("Spliterator should be exhausted");
        }
    }
}
