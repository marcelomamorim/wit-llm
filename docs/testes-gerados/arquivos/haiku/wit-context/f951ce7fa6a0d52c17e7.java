/*
 * @test
 * @summary Verifies Stream.Builder.add through the public Stream.builder() API: fluent return, element order, and IllegalStateException after build
 * @run main StreamsWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testAddReturnsSameBuilder();
        testAddPreservesOrder();
        testAddAfterBuildThrows();
        testSecondBuildThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAddReturnsSameBuilder() {
        Stream.Builder<String> builder = Stream.builder();
        check(builder.add("a") == builder, "add must return the same builder instance");
    }

    static void testAddPreservesOrder() {
        List<String> result = Stream.<String>builder()
                .add("a")
                .add("b")
                .add("c")
                .build()
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList("a", "b", "c")), "elements must keep insertion order: " + result);
    }

    static void testAddAfterBuildThrows() {
        Stream.Builder<Integer> builder = Stream.builder();
        builder.add(1);
        builder.build();
        try {
            builder.add(2);
            throw new AssertionError("add after build must throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testSecondBuildThrows() {
        Stream.Builder<Integer> builder = Stream.builder();
        builder.add(1);
        builder.build();
        try {
            builder.build();
            throw new AssertionError("second build must throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
