/* @test    @summary Test DoubleStream.iterate method for correctness and regressions    @run main DoubleStreamWitupTest */

import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testIterateBasic();
        testIterateWithFunction();
        testIterateWithNullFunction();
        testIterateWithLargeCount();
    }

    static void testIterateBasic() {
        DoubleUnaryOperator plusOne = x -> x + 1;
        DoubleStream stream = DoubleStream.iterate(0.0, plusOne);
        double[] result = stream.limit(5).toArray();
        double[] expected = {0.0, 1.0, 2.0, 3.0, 4.0};
        assert java.util.Arrays.equals(result, expected) : "Basic iterate test failed";
    }

    static void testIterateWithFunction() {
        DoubleUnaryOperator multiplyByTwo = x -> x * 2;
        DoubleStream stream = DoubleStream.iterate(1.0, multiplyByTwo);
        double[] result = stream.limit(4).toArray();
        double[] expected = {1.0, 2.0, 4.0, 8.0};
        assert java.util.Arrays.equals(result, expected) : "Iterate with function test failed";
    }

    static void testIterateWithNullFunction() {
        try {
            DoubleStream.iterate(0.0, null);
            assert false : "Expected NullPointerException for null function";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testIterateWithLargeCount() {
        DoubleUnaryOperator addOne = x -> x + 1;
        DoubleStream stream = DoubleStream.iterate(0.0, addOne);
        double[] result = stream.limit(1000).toArray();
        assert result.length == 1000 : "Large count limit failed";
        assert result[0] == 0.0 : "First element incorrect";
        assert result[999] == 999.0 : "Last element incorrect";
    }
}
