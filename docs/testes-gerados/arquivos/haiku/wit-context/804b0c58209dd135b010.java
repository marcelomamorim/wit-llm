/*
 * @test
 * @summary Witup-generated tests for Stream.iterate(seed, f)
 * @run main StreamWitupTest
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testSequenceGeneration();
        testNullSeedIsAllowed();
        testFunctionNotInvokedAtCreation();
    }

    static void testNullFunctionThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.iterate(1, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException when f is null");
        }
    }

    static void testSequenceGeneration() {
        List<Integer> result = Stream.iterate(1, x -> x * 2)
                .limit(5)
                .collect(Collectors.toList());
        List<Integer> expected = java.util.Arrays.asList(1, 2, 4, 8, 16);
        if (!expected.equals(result)) {
            throw new RuntimeException("Unexpected sequence: " + result);
        }
    }

    static void testNullSeedIsAllowed() {
        List<String> result = Stream.iterate(null, x -> x == null ? "next" : x + "!")
                .limit(2)
                .collect(Collectors.toList());
        if (result.size() != 2 || result.get(0) != null || !"next".equals(result.get(1))) {
            throw new RuntimeException("Unexpected result with null seed: " + result);
        }
    }

    static void testFunctionNotInvokedAtCreation() {
        final int[] calls = {0};
        Stream<Integer> s = Stream.iterate(0, x -> {
            calls[0]++;
            return x + 1;
        });
        if (calls[0] != 0) {
            throw new RuntimeException("Function invoked eagerly: " + calls[0]);
        }
        List<Integer> first = s.limit(3).collect(Collectors.toList());
        if (!java.util.Arrays.asList(0, 1, 2).equals(first)) {
            throw new RuntimeException("Unexpected lazy sequence: " + first);
        }
        if (calls[0] != 2) {
            throw new RuntimeException("Unexpected invocation count: " + calls[0]);
        }
    }
}
