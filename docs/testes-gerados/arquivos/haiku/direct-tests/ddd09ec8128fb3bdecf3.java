/*
 * @test
 * @summary Verifies Streams.StreamBuilderImpl.forEachRemaining through the public Stream.builder() API
 * @run main StreamsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testSingleElementBuilder();
        testEmptyBuilder();
    }

    static void testSingleElementBuilder() {
        Stream.Builder<String> builder = Stream.builder();
        builder.add("x");
        List<String> got = new ArrayList<>();
        builder.build().forEach(got::add);
        if (!got.equals(List.of("x"))) {
            throw new RuntimeException("Expected [x] but got " + got);
        }
    }

    static void testEmptyBuilder() {
        Stream.Builder<String> builder = Stream.builder();
        List<String> got = new ArrayList<>();
        builder.build().forEach(got::add);
        if (!got.isEmpty()) {
            throw new RuntimeException("Expected empty but got " + got);
        }
    }
}
