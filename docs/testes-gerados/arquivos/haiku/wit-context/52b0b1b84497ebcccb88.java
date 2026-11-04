/*
 * @test
 * @summary Verify IntStream.Builder accept sequencing and IllegalStateException after build, which reaches Streams.IntStreamBuilderImpl.accept(int)
 * @run main StreamsWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testEmptyBuild();
        testSingleElement();
        testMultipleElements();
        testAcceptAfterBuildThrows();
    }

    private static void testEmptyBuild() {
        IntStream.Builder builder = IntStream.builder();
        int[] result = builder.build().toArray();
        check(result.length == 0, "empty builder should build an empty stream");
    }

    private static void testSingleElement() {
        IntStream.Builder builder = IntStream.builder();
        builder.accept(42);
        int[] result = builder.build().toArray();
        check(Arrays.equals(result, new int[] {42}), "single element should be preserved");
    }

    private static void testMultipleElements() {
        IntStream.Builder builder = IntStream.builder();
        builder.accept(3);
        builder.accept(1);
        builder.accept(2);
        builder.accept(Integer.MIN_VALUE);
        builder.accept(Integer.MAX_VALUE);
        int[] result = builder.build().toArray();
        check(Arrays.equals(result,
                new int[] {3, 1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE}),
                "elements should be preserved in insertion order");
    }

    private static void testAcceptAfterBuildThrows() {
        IntStream.Builder builder = IntStream.builder();
        builder.accept(7);
        builder.build();
        boolean thrown = false;
        try {
            builder.accept(8);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "accept after build should throw IllegalStateException");

        IntStream.Builder emptyBuilder = IntStream.builder();
        emptyBuilder.build();
        thrown = false;
        try {
            emptyBuilder.accept(9);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "accept on an empty built builder should throw IllegalStateException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
