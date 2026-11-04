/*
 * @test
 * @summary Test Streams.IntStreamBuilderImpl.accept(int) for normal and exceptional behavior
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.stream.Streams.IntStreamBuilderImpl;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAcceptNormalBehavior();
        testAcceptThrowsIllegalStateException();
    }

    private static void testAcceptNormalBehavior() {
        IntStreamBuilderImpl builder = new IntStreamBuilderImpl();

        // Initially count == 0, accept first element
        builder.accept(10);

        // Accept second element, triggers buffer creation
        builder.accept(20);

        // Accept third element, goes to buffer
        builder.accept(30);

        // No exception expected, no direct way to observe internal state,
        // so just ensure no exception thrown and method returns normally.
    }

    private static void testAcceptThrowsIllegalStateException() {
        IntStreamBuilderImpl builder = new IntStreamBuilderImpl();

        // Force count to negative to trigger exception
        // No public setter, so simulate by calling accept to set count > 0,
        // then use reflection to set count to -1 (allowed since count is private)
        // Reflection is necessary here because no public API to set count < 0
        try {
            java.lang.reflect.Field countField = IntStreamBuilderImpl.class.getDeclaredField("count");
            countField.setAccessible(true);

            // Set count to -1 to trigger exception
            countField.setInt(builder, -1);

            // Now call accept, expect IllegalStateException
            try {
                builder.accept(5);
                throw new RuntimeException("Expected IllegalStateException not thrown");
            } catch (IllegalStateException expected) {
                // Expected exception caught
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
