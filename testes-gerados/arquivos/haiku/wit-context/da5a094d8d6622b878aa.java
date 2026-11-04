/*
 * @test
 * @summary Witup-generated checks for ReferencePipeline.forEachOrdered via public Stream API
 * @run main ReferencePipelineWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testOrderedVisit();
        testParallelOrderedVisit();
        testNullActionThrowsNPE();
        testReuseThrowsIllegalStateException();
    }

    static void testOrderedVisit() {
        List<String> out = new ArrayList<>();
        Stream.of("a", "b", "c").forEachOrdered(out::add);
        check(out.equals(Arrays.asList("a", "b", "c")), "encounter order violated: " + out);
    }

    static void testParallelOrderedVisit() {
        List<Integer> out = new ArrayList<>();
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8).parallel().forEachOrdered(out::add);
        check(out.equals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)),
              "parallel forEachOrdered not ordered: " + out);
    }

    static void testNullActionThrowsNPE() {
        try {
            Stream.of(1).forEachOrdered(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReuseThrowsIllegalStateException() {
        Stream<String> s = Stream.of("x");
        s.forEachOrdered(x -> { });
        try {
            s.forEachOrdered(x -> { });
            throw new RuntimeException("expected IllegalStateException on reused stream");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
