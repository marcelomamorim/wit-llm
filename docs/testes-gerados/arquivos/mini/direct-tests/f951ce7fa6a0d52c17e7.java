/*
 * @test
 * @summary Test Streams.Builder add(T) method behavior
 * @run main StreamsWitupTest
 */

import java.util.stream.Stream;

public class StreamsWitupTest {
    public static void main(String[] args) {
        testAddReturnsSameBuilder();
        testAddAcceptsNull();
    }

    private static void testAddReturnsSameBuilder() {
        Stream.Builder<String> builder = Stream.builder();
        Stream.Builder<String> returned = builder.add("test");
        if (returned != builder) {
            throw new AssertionError("add(T) should return the same builder instance");
        }
    }

    private static void testAddAcceptsNull() {
        Stream.Builder<String> builder = Stream.builder();
        // add(null) should accept null and return the builder without exception
        Stream.Builder<String> returned = builder.add(null);
        if (returned != builder) {
            throw new AssertionError("add(null) should return the same builder instance");
        }
        // Build stream and check it contains the null element
        Stream<String> stream = builder.build();
        Object[] elements = stream.toArray();
        if (elements.length != 1 || elements[0] != null) {
            throw new AssertionError("Stream should contain the null element added");
        }
    }
}
