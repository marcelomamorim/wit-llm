/*
 * @test
 * @summary Basic behavior of Stream.generate: lazy supplier invocation, element values, and NPE on null supplier
 * @run main StreamWitupTest
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testLazyCreation();
        testElementsFromSupplier();
        testNullSupplierThrowsNPE();
    }

    static void testLazyCreation() {
        int[] calls = {0};
        Stream<String> s = Stream.generate(() -> {
            calls[0]++;
            return "x";
        });
        if (calls[0] != 0) {
            throw new RuntimeException("supplier invoked eagerly: " + calls[0]);
        }
        if (s == null) {
            throw new RuntimeException("generate returned null");
        }
    }

    static void testElementsFromSupplier() {
        int[] counter = {0};
        List<Integer> values = Stream.generate(() -> ++counter[0])
                .limit(3)
                .collect(Collectors.toList());
        if (values.size() != 3) {
            throw new RuntimeException("unexpected size: " + values.size());
        }
        for (int i = 0; i < 3; i++) {
            if (values.get(i) != i + 1) {
                throw new RuntimeException("unexpected value at " + i + ": " + values.get(i));
            }
        }
        if (counter[0] != 3) {
            throw new RuntimeException("unexpected supplier calls: " + counter[0]);
        }
    }

    static void testNullSupplierThrowsNPE() {
        try {
            Stream.generate(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
