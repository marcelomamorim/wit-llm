/*
 * @test
 * @summary Test java.util.stream.Streams.LongStreamBuilderImpl.accept(long) for correct behavior and exception
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.stream.Streams.LongStreamBuilderImpl;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAcceptNormalBehavior();
        testAcceptThrowsOnNegativeCount();
    }

    private static void testAcceptNormalBehavior() {
        LongStreamBuilderImpl builder = new LongStreamBuilderImpl();

        // Initially count == 0, accept first element
        builder.accept(10L);

        // Accept second element, triggers buffer creation
        builder.accept(20L);

        // Accept third element, goes to buffer
        builder.accept(30L);

        // No exception expected, no direct way to assert internal state,
        // but no exception means pass
    }

    private static void testAcceptThrowsOnNegativeCount() {
        LongStreamBuilderImpl builder = new LongStreamBuilderImpl();

        // Use reflection to set count to negative to simulate error state
        // Reflection is used because count is private and no public setter
        try {
            java.lang.reflect.Field countField = LongStreamBuilderImpl.class.getDeclaredField("count");
            countField.setAccessible(true);
            countField.setInt(builder, -1);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }

        boolean thrown = false;
        try {
            builder.accept(42L);
        } catch (IllegalStateException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when count < 0");
        }
    }
}
