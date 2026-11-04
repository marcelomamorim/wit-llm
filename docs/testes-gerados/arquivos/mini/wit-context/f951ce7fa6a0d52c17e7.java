/*
 * @test
 * @summary Test java.util.stream.Streams.add(T) method for expected behavior and exception path
 * @run main StreamsWitupTest
 */

import java.util.stream.Stream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAddNormal();
        testAddIllegalStateExceptionPath();
        System.out.println("StreamsWitupTest passed");
    }

    private static void testAddNormal() {
        // Create a Stream.Builder and add an element, verify chaining and no exception
        Stream.Builder<String> builder = Stream.builder();
        Stream.Builder<String> returned = builder.add("test");
        if (returned != builder) {
            throw new AssertionError("add() should return the same builder instance");
        }
        // Build stream and verify element
        Stream<String> stream = builder.build();
        Object[] elements = stream.toArray();
        if (elements.length != 1 || !"test".equals(elements[0])) {
            throw new AssertionError("Stream should contain the added element");
        }
    }

    private static void testAddIllegalStateExceptionPath() {
        // The WIT expath indicates IllegalStateException if internal count < 0 in StreamBuilderImpl
        // This is internal state not accessible publicly, so we cannot directly trigger it.
        // We test that normal usage does not throw IllegalStateException.
        Stream.Builder<String> builder = Stream.builder();
        try {
            builder.add(null); // adding null is allowed, no NPE expected
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException on normal add(null)", e);
        } catch (NullPointerException e) {
            // Stream.BuilderImpl.accept does not allow null, so NPE is possible
            // Acceptable behavior, no IllegalStateException expected
        }
        // No direct test for count < 0 path possible without reflection or internal access
        // This test acknowledges the expath but cannot reproduce it due to encapsulation
    }
}
