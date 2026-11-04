/*
 * @test
 * @summary Behavioral checks for ReduceOps.makeInt reached through the public IntStream.reduce API
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.IntStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testEmptyStreamReturnsIdentity();
        testSumOfElements();
        testIdentityIsUsedOnce();
        testSequentialLeftToRightOrder();
        testParallelSumMatchesExpected();
        testParallelMaxWithMinValueIdentity();
        testNullOperatorThrowsNpe();
    }

    static void testEmptyStreamReturnsIdentity() {
        int r = IntStream.empty().reduce(7, (a, b) -> a + b);
        check(r == 7, "empty stream should return identity, got " + r);
    }

    static void testSumOfElements() {
        int r = IntStream.of(1, 2, 3, 4).reduce(0, (a, b) -> a + b);
        check(r == 10, "sum expected 10, got " + r);
    }

    static void testIdentityIsUsedOnce() {
        int r = IntStream.of(1, 2, 3).reduce(10, (a, b) -> a + b);
        check(r == 16, "identity 10 plus 1+2+3 expected 16, got " + r);
    }

    static void testSequentialLeftToRightOrder() {
        int r = IntStream.of(1, 2, 3).reduce(0, (a, b) -> a - b);
        check(r == -6, "left-to-right subtraction expected -6, got " + r);
    }

    static void testParallelSumMatchesExpected() {
        int r = IntStream.rangeClosed(1, 1000).parallel().reduce(0, (a, b) -> a + b);
        check(r == 500500, "parallel sum expected 500500, got " + r);
    }

    static void testParallelMaxWithMinValueIdentity() {
        int r = IntStream.rangeClosed(1, 1000).parallel().reduce(Integer.MIN_VALUE, Math::max);
        check(r == 1000, "parallel max expected 1000, got " + r);
    }

    static void testNullOperatorThrowsNpe() {
        boolean thrown = false;
        try {
            IntStream.of(1).reduce(0, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null operator should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
