/*
 * @test
 * @summary Verifies ReferencePipeline.forEachOrdered visits elements in encounter order for sequential and parallel streams
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testSequentialOrder();
        testParallelOrder();
        testEmptyStream();
        testAfterIntermediateOps();
    }

    static void testSequentialOrder() {
        List<Integer> out = new ArrayList<>();
        Stream.of(1, 2, 3, 4, 5).forEachOrdered(out::add);
        check(out.equals(Arrays.asList(1, 2, 3, 4, 5)), "sequential order: " + out);
    }

    static void testParallelOrder() {
        List<Integer> out = new ArrayList<>();
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
              .parallel()
              .forEachOrdered(out::add);
        check(out.equals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
              "parallel order: " + out);
    }

    static void testEmptyStream() {
        List<Integer> out = new ArrayList<>();
        Stream.<Integer>empty().forEachOrdered(out::add);
        check(out.isEmpty(), "empty stream produced: " + out);
    }

    static void testAfterIntermediateOps() {
        List<Integer> out = new ArrayList<>();
        Stream.of("a", "bb", "ccc", "dd")
              .map(String::length)
              .filter(i -> i > 1)
              .forEachOrdered(out::add);
        check(out.equals(Arrays.asList(2, 3, 2)), "after map/filter: " + out);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
