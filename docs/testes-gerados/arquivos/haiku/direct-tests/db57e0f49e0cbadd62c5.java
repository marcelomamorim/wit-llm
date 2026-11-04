/*
 * @test
 * @summary Verifies DoubleStream.iterate sequence generation, laziness and null-function handling
 * @run main DoubleStreamWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testDoublingSequence();
        testSeedIsFirstElementWithoutApplyingFunction();
        testFunctionAppliedLazilyAndDeterministically();
        testNullFunctionThrowsNullPointerException();
    }

    static void testDoublingSequence() {
        double[] r = DoubleStream.iterate(1.0, x -> x * 2).limit(4).toArray();
        check(Arrays.equals(r, new double[]{1.0, 2.0, 4.0, 8.0}), "doubling sequence");
    }

    static void testSeedIsFirstElementWithoutApplyingFunction() {
        int[] calls = {0};
        double first = DoubleStream.iterate(5.0, x -> {
            calls[0]++;
            return x + 1;
        }).findFirst().getAsDouble();
        check(first == 5.0, "seed must be the first element");
        check(calls[0] == 0, "function must not be applied to produce the seed");
    }

    static void testFunctionAppliedLazilyAndDeterministically() {
        int[] calls = {0};
        double[] r = DoubleStream.iterate(5.0, x -> {
            calls[0]++;
            return x + 1;
        }).limit(3).toArray();
        check(Arrays.equals(r, new double[]{5.0, 6.0, 7.0}), "incremental sequence");
        check(calls[0] == 2, "function must be applied once per element after the seed");

        double[] again = DoubleStream.iterate(5.0, x -> x + 1).limit(3).toArray();
        check(Arrays.equals(again, r), "new stream must produce the same sequence");
    }

    static void testNullFunctionThrowsNullPointerException() {
        boolean thrown = false;
        try {
            DoubleStream.iterate(0.0, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null function must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
