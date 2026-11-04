/*
 * @test
 * @summary Test IntPipeline.noneMatch method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testNoneMatchWithEmptyStream();
        testNoneMatchWithNoMatch();
        testNoneMatchWithSomeMatch();
    }

    private static void testNoneMatchWithEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        boolean result = emptyStream.noneMatch(new IntPredicate() {
            public boolean test(int value) {
                return value > 0;
            }
        });
        if (!result) {
            throw new AssertionError("noneMatch should return true for empty stream");
        }
    }

    private static void testNoneMatchWithNoMatch() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        boolean result = stream.noneMatch(new IntPredicate() {
            public boolean test(int value) {
                return value > 10;
            }
        });
        if (!result) {
            throw new AssertionError("noneMatch should return true when no elements match the predicate");
        }
    }

    private static void testNoneMatchWithSomeMatch() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        boolean result = stream.noneMatch(new IntPredicate() {
            public boolean test(int value) {
                return value == 3;
            }
        });
        if (result) {
            throw new AssertionError("noneMatch should return false when some elements match the predicate");
        }
    }
}
