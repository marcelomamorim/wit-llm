/*
 * @test
 * @summary Test IntPipeline.allMatch method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchTrue();
        testAllMatchFalse();
        testAllMatchEmpty();
    }

    private static void testAllMatchTrue() {
        IntStream stream = IntStream.of(2, 4, 6, 8);
        boolean result = stream.allMatch(new IntPredicate() {
            public boolean test(int value) {
                return value % 2 == 0;
            }
        });
        if (!result) {
            throw new AssertionError("Expected allMatch to return true for all even numbers");
        }
    }

    private static void testAllMatchFalse() {
        IntStream stream = IntStream.of(1, 2, 3, 4);
        boolean result = stream.allMatch(new IntPredicate() {
            public boolean test(int value) {
                return value % 2 == 0;
            }
        });
        if (result) {
            throw new AssertionError("Expected allMatch to return false when not all elements match predicate");
        }
    }

    private static void testAllMatchEmpty() {
        IntStream stream = IntStream.empty();
        boolean result = stream.allMatch(new IntPredicate() {
            public boolean test(int value) {
                return false;
            }
        });
        if (!result) {
            throw new AssertionError("Expected allMatch to return true for empty stream");
        }
    }
}
