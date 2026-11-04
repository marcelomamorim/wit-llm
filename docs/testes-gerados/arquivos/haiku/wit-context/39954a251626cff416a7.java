/*
 * @test
 * @summary Witup generated tests for DoublePipeline.peek via public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testPeekObservesElementsInOrder();
        testPeekPreservesElements();
        testPeekIsLazyUntilTerminalOperation();
    }

    static void testNullActionThrowsNullPointerException() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).peek(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError();
        }
    }

    static void testPeekObservesElementsInOrder() {
        List<Double> seen = new ArrayList<>();
        double sum = DoubleStream.of(1.5, 2.5, 3.0).peek(d -> seen.add(d)).sum();
        if (sum != 7.0) {
            throw new AssertionError();
        }
        if (seen.size() != 3
                || seen.get(0) != 1.5
                || seen.get(1) != 2.5
                || seen.get(2) != 3.0) {
            throw new AssertionError();
        }
    }

    static void testPeekPreservesElements() {
        double[] result = DoubleStream.of(4.0, 5.0).peek(d -> {}).toArray();
        if (result.length != 2 || result[0] != 4.0 || result[1] != 5.0) {
            throw new AssertionError();
        }
    }

    static void testPeekIsLazyUntilTerminalOperation() {
        List<Double> seen = new ArrayList<>();
        DoubleStream s = DoubleStream.of(1.0).peek(d -> seen.add(d));
        if (!seen.isEmpty()) {
            throw new AssertionError();
        }
        s.sum();
        if (seen.size() != 1) {
            throw new AssertionError();
        }
    }
}
