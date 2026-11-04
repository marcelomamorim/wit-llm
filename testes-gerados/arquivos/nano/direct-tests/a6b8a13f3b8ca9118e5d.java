/*
 * @test
 * @summary Test for LongStream.iterate method to ensure correct sequence generation.
 * @run main LongStreamWitupTest
 */
import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testIterateWithPositiveIncrement();
        testIterateWithNegativeIncrement();
        testIterateWithZeroIncrement();
        testIterateWithLargeValues();
    }

    static void testIterateWithPositiveIncrement() {
        LongUnaryOperator plusOne = x -> x + 1;
        LongStream stream = LongStream.iterate(0L, plusOne);
        long[] expected = {0L, 1L, 2L, 3L, 4L, 5L};
        long[] actual = stream.limit(6).toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed positive increment test";
    }

    static void testIterateWithNegativeIncrement() {
        LongUnaryOperator minusOne = x -> x - 1;
        LongStream stream = LongStream.iterate(5L, minusOne);
        long[] expected = {5L, 4L, 3L, 2L, 1L, 0L};
        long[] actual = stream.limit(6).toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed negative decrement test";
    }

    static void testIterateWithZeroIncrement() {
        LongUnaryOperator zeroOperator = x -> 0L;
        LongStream stream = LongStream.iterate(10L, zeroOperator);
        long[] expected = {10L, 0L, 0L, 0L, 0L, 0L};
        long[] actual = stream.limit(6).toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed zero increment test";
    }

    static void testIterateWithLargeValues() {
        LongUnaryOperator addTen = x -> x + 10;
        LongStream stream = LongStream.iterate(1000L, addTen);
        long[] expected = {1000L, 1010L, 1020L, 1030L, 1040L};
        long[] actual = stream.limit(5).toArray();
        assert java.util.Arrays.equals(expected, actual) : "Failed large values test";
    }
}
