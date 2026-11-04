/*
 * @test
 * @summary Verifies observable behavior of DoublePipeline.peek through the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testPeekObservesElementsInOrder();
        testPeekIsLazyUntilTerminalOperation();
        testPeekDoesNotChangeSum();
        testPeekAfterMapSeesMappedValues();
        testPeekNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPeekObservesElementsInOrder() {
        List<Double> seen = new ArrayList<>();
        DoubleStream.of(1.5, 2.5, 3.5).peek(t -> seen.add(t)).toArray();
        check(seen.size() == 3, "expected 3 peeked elements but got " + seen.size());
        check(seen.get(0) == 1.5, "first element wrong: " + seen.get(0));
        check(seen.get(1) == 2.5, "second element wrong: " + seen.get(1));
        check(seen.get(2) == 3.5, "third element wrong: " + seen.get(2));
    }

    static void testPeekIsLazyUntilTerminalOperation() {
        List<Double> seen = new ArrayList<>();
        DoubleStream s = DoubleStream.of(1.0, 2.0).peek(t -> seen.add(t));
        check(seen.isEmpty(), "peek action ran before terminal operation");
        s.sum();
        check(seen.size() == 2, "peek action did not run on terminal operation");
    }

    static void testPeekDoesNotChangeSum() {
        double sum = DoubleStream.of(1.5, 2.5, 3.5).peek(t -> { }).sum();
        check(sum == 7.5, "sum changed by peek: " + sum);
    }

    static void testPeekAfterMapSeesMappedValues() {
        List<Double> seen = new ArrayList<>();
        double[] result = DoubleStream.of(1.0, 2.0)
                .map(x -> x * 2)
                .peek(t -> seen.add(t))
                .toArray();
        check(result.length == 2, "unexpected result length " + result.length);
        check(result[0] == 2.0 && result[1] == 4.0, "unexpected mapped result");
        check(seen.size() == 2 && seen.get(0) == 2.0 && seen.get(1) == 4.0,
              "peek did not observe mapped values");
    }

    static void testPeekNullActionThrowsNPE() {
        try {
            DoubleStream.of(1.0).peek(null);
            check(false, "expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
