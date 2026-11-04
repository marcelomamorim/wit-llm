/*
 * @test
 * @summary Verifies IntStream.max(), which is implemented by IntPipeline.max(), through the public API
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMaxOfUnsortedValues();
        testMaxOfEmptyStreamIsEmpty();
        testMaxOfSingleNegativeValue();
        testMaxWithDuplicates();
        testMaxWithBoundaryValues();
        testMaxOfRange();
    }

    static void testMaxOfUnsortedValues() {
        OptionalInt result = IntStream.of(3, 1, 2).max();
        check(result.isPresent(), "max of non-empty stream must be present");
        checkEquals(3, result.getAsInt(), "max of {3,1,2}");
    }

    static void testMaxOfEmptyStreamIsEmpty() {
        OptionalInt result = IntStream.empty().max();
        check(!result.isPresent(), "max of empty stream must be empty");
    }

    static void testMaxOfSingleNegativeValue() {
        OptionalInt result = IntStream.of(-5).max();
        check(result.isPresent(), "max of single element must be present");
        checkEquals(-5, result.getAsInt(), "max of {-5}");
    }

    static void testMaxWithDuplicates() {
        OptionalInt result = IntStream.of(7, 7, 7).max();
        check(result.isPresent(), "max with duplicates must be present");
        checkEquals(7, result.getAsInt(), "max of {7,7,7}");
    }

    static void testMaxWithBoundaryValues() {
        OptionalInt result = IntStream.of(Integer.MIN_VALUE, Integer.MAX_VALUE).max();
        check(result.isPresent(), "max with boundary values must be present");
        checkEquals(Integer.MAX_VALUE, result.getAsInt(), "max of {MIN_VALUE,MAX_VALUE}");

        OptionalInt onlyMin = IntStream.of(Integer.MIN_VALUE).max();
        check(onlyMin.isPresent(), "max of {MIN_VALUE} must be present");
        checkEquals(Integer.MIN_VALUE, onlyMin.getAsInt(), "max of {MIN_VALUE}");
    }

    static void testMaxOfRange() {
        OptionalInt result = IntStream.range(0, 5).max();
        check(result.isPresent(), "max of range must be present");
        checkEquals(4, result.getAsInt(), "max of range(0,5)");
    }

    static void checkEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
