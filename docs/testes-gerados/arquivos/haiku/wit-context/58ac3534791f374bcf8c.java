/*
 * @test
 * @summary Tests IntStream.generate for null supplier rejection and observable supplier values
 * @run main IntStreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testGenerateConstantSupplier();
        testGenerateSequentialSupplier();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSupplierThrowsNPE() {
        boolean thrown = false;
        try {
            IntStream.generate(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "generate(null) must throw NullPointerException");
    }

    static void testGenerateConstantSupplier() {
        int[] result = IntStream.generate(() -> 7).limit(3).toArray();
        check(Arrays.equals(result, new int[] {7, 7, 7}),
              "generate(() -> 7).limit(3) must yield {7, 7, 7} but was " + Arrays.toString(result));
    }

    static void testGenerateSequentialSupplier() {
        final int[] counter = {0};
        int[] result = IntStream.generate(() -> counter[0]++).limit(4).toArray();
        check(Arrays.equals(result, new int[] {0, 1, 2, 3}),
              "generate with counter supplier must yield {0, 1, 2, 3} but was " + Arrays.toString(result));
    }
}
