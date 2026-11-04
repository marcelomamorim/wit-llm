/*
 * @test
 * @summary Verify LongStream.generate produces lazy, ordered, infinite-supplier streams and rejects null suppliers
 * @run main LongStreamWitupTest
 */

import java.util.Arrays;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testLimitedValuesInOrder();
        testSupplierCalledOnlyAsNeeded();
        testConstantSupplierSum();
        testResultIsSequential();
        testNullSupplierThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testLimitedValuesInOrder() {
        long[] counter = {0L};
        long[] result = LongStream.generate(() -> counter[0]++)
                                  .limit(5)
                                  .toArray();
        long[] expected = {0L, 1L, 2L, 3L, 4L};
        check(Arrays.equals(expected, result),
              "Expected values 0..4 in order but got " + Arrays.toString(result));
    }

    static void testSupplierCalledOnlyAsNeeded() {
        long[] calls = {0L};
        LongStream stream = LongStream.generate(() -> {
            calls[0]++;
            return 7L;
        });
        check(calls[0] == 0L, "Supplier must not be invoked before a terminal operation");
        long[] result = stream.limit(3).toArray();
        check(result.length == 3, "Expected 3 elements but got " + result.length);
        check(calls[0] == 3L, "Expected supplier called 3 times but was " + calls[0]);
    }

    static void testConstantSupplierSum() {
        long sum = LongStream.generate(() -> 5L).limit(4).sum();
        check(sum == 20L, "Expected sum 20 but got " + sum);
    }

    static void testResultIsSequential() {
        boolean parallel = LongStream.generate(() -> 1L).limit(2).isParallel();
        check(!parallel, "Stream returned by generate should be sequential");
    }

    static void testNullSupplierThrowsNPE() {
        try {
            LongStream.generate(null);
            throw new RuntimeException("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
