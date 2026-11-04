/*
 * @test
 * @summary Tests for IntStream.iterate method
 * @run main IntStreamWitupTest
 */
import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testIterateWithPositiveIncrement();
        testIterateWithNegativeIncrement();
        testIterateWithZeroIncrement();
        testIterateWithLargeValues();
    }

    static void testIterateWithPositiveIncrement() {
        IntUnaryOperator plusOne = x -> x + 1;
        IntStream stream = IntStream.iterate(0, plusOne);
        int[] result = stream.limit(5).toArray();
        int[] expected = {0, 1, 2, 3, 4};
        assert java.util.Arrays.equals(result, expected) : "Failed: iterate with positive increment";
    }

    static void testIterateWithNegativeIncrement() {
        IntUnaryOperator minusOne = x -> x - 1;
        IntStream stream = IntStream.iterate(0, minusOne);
        int[] result = stream.limit(3).toArray();
        int[] expected = {0, -1, -2};
        assert java.util.Arrays.equals(result, expected) : "Failed: iterate with negative increment";
    }

    static void testIterateWithZeroIncrement() {
        IntUnaryOperator zeroOperator = x -> x;
        IntStream stream = IntStream.iterate(42, zeroOperator);
        int[] result = stream.limit(4).toArray();
        int[] expected = {42, 42, 42, 42};
        assert java.util.Arrays.equals(result, expected) : "Failed: iterate with zero increment";
    }

    static void testIterateWithLargeValues() {
        IntUnaryOperator addTen = x -> x + 10;
        IntStream stream = IntStream.iterate(1000, addTen);
        int[] result = stream.limit(3).toArray();
        int[] expected = {1000, 1010, 1020};
        assert java.util.Arrays.equals(result, expected) : "Failed: iterate with large values";
    }
}
