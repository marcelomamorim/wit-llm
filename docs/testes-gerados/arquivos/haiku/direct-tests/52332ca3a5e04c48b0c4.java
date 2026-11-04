/*
 * @test
 * @summary IntStream.iterate(int, IntUnaryOperator) produces seed first, applies f lazily, and rejects null f
 * @run main IntStreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSequence() {
        int[] arr = IntStream.iterate(1, x -> x * 2).limit(5).toArray();
        check(Arrays.equals(arr, new int[] {1, 2, 4, 8, 16}),
              "unexpected sequence: " + Arrays.toString(arr));
    }

    static void testSeedReturnedWithoutApplyingF() {
        int[] arr = IntStream.iterate(7, x -> {
            throw new AssertionError("f must not be applied to seed");
        }).limit(1).toArray();
        check(arr.length == 1 && arr[0] == 7, "seed not returned first");
    }

    static void testNullFailsEagerly() {
        try {
            IntStream.iterate(0, null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(f) runs at call time
        }
    }

    static void testLazyCallCount() {
        int[] calls = new int[1];
        IntStream s = IntStream.iterate(0, x -> {
            calls[0]++;
            return x + 1;
        });
        check(calls[0] == 0, "f invoked before terminal operation");
        int[] arr = s.limit(3).toArray();
        check(Arrays.equals(arr, new int[] {0, 1, 2}),
              "unexpected values: " + Arrays.toString(arr));
        check(calls[0] == 2, "expected f called 2 times, was " + calls[0]);
    }

    static void testIntegerOverflowWraps() {
        int[] arr = IntStream.iterate(Integer.MAX_VALUE, x -> x + 1).limit(2).toArray();
        check(arr[0] == Integer.MAX_VALUE && arr[1] == Integer.MIN_VALUE,
              "unexpected overflow behavior: " + Arrays.toString(arr));
    }

    static void testInfiniteStreamSum() {
        int sum = IntStream.iterate(0, x -> x + 1).limit(10).sum();
        check(sum == 45, "unexpected sum: " + sum);
    }

    public static void main(String[] args) {
        testSequence();
        testSeedReturnedWithoutApplyingF();
        testNullFailsEagerly();
        testLazyCallCount();
        testIntegerOverflowWraps();
        testInfiniteStreamSum();
    }
}
