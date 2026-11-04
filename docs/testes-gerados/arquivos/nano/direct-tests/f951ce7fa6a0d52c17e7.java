/* @test    @summary Test the add method of Streams class for correct chaining and behavior    @run main StreamsWitupTest */

import java.util.stream.Stream;
import java.util.stream.Streams;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAddMethodReturnsSelf();
        testAddMethodAcceptsNull();
    }

    private static void testAddMethodReturnsSelf() {
        Stream.Builder<String> builder = Stream.builder();
        Stream.Builder<String> result = builder.add("test");
        if (result != builder) {
            throw new AssertionError("add() should return the same builder instance") ;
        }
    }

    private static void testAddMethodAcceptsNull() {
        Stream.Builder<String> builder = Stream.builder();
        try {
            builder.add(null);
        } catch (NullPointerException e) {
            throw new AssertionError("add() should accept null without throwing") ;
        }
        // Verify that the builder contains null by building the stream
        Stream<String> stream = builder.build();
        if (!stream.iterator().hasNext()) {
            throw new AssertionError("Stream should contain null after adding null") ;
        }
    }
}
