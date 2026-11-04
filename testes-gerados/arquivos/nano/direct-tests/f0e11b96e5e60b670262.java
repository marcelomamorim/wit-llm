/* @test    @summary Test for Spliterators.forEachRemaining method to ensure it handles null consumers properly.    @run main SpliteratorsWitupTest */

import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
    }

    public static void testForEachRemainingNullConsumer() {
        try {
            Spliterators.forEachRemaining(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
