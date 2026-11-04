/*
 * @test
 * @summary Test for ThreadLocalRandom.doubles() method to observe behavior and exception handling.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesBehavior();
    }

    static void testDoublesReturnsStream() {
        DoubleStream ds = ThreadLocalRandom.current().doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }
        System.out.println("doubles() returned a non-null DoubleStream.");
    }

    static void testDoublesBehavior() {
        DoubleStream ds = ThreadLocalRandom.current().doubles();
        try {
            boolean hasNext = ds.iterator().hasNext();
            System.out.println("Stream has next: " + hasNext);
        } catch (Exception e) {
            throw new AssertionError("Exception while iterating doubles stream", e);
        }
    }
}
