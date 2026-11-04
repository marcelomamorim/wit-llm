/*
 * @test
 * @summary Behavioral checks for DoubleStream.iterate(double, DoubleUnaryOperator): null function rejection, element sequence, and lazy function application
 * @run main DoubleStreamWitupTest
 */
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testSequenceValues();
        testFunctionNotCalledBeforeNeeded();
    }

    static void check(boolean condition, int code) {
        if (!condition) {
            throw new RuntimeException("check failed: " + code);
        }
    }

    static void testNullFunctionThrowsNPE() {
        boolean thrown = false;
        try {
            DoubleStream.iterate(1.0, (DoubleUnaryOperator) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, 1);
    }

    static void testSequenceValues() {
        double[] values = DoubleStream.iterate(1.0, x -> x * 2).limit(4).toArray();
        check(Arrays.equals(values, new double[] {1.0, 2.0, 4.0, 8.0}), 2);

        double[] same = DoubleStream.iterate(3.5, x -> x).limit(3).toArray();
        check(Arrays.equals(same, new double[] {3.5, 3.5, 3.5}), 3);
    }

    static void testFunctionNotCalledBeforeNeeded() {
        int[] calls = new int[1];
        DoubleStream stream = DoubleStream.iterate(1.0, x -> {
            calls[0]++;
            return x + 1;
        });
        check(calls[0] == 0, 4);

        double[] first = stream.limit(1).toArray();
        check(first.length == 1 && first[0] == 1.0, 5);
        check(calls[0] == 0, 6);

        double[] three = DoubleStream.iterate(1.0, x -> {
            calls[0]++;
            return x + 1;
        }).limit(3).toArray();
        check(Arrays.equals(three, new double[] {1.0, 2.0, 3.0}), 7);
        check(calls[0] >= 2, 8);
    }
}
