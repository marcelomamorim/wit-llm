/*
 * @test
 * @summary Verify Stream.generate rejects a null supplier and produces elements from the supplier
 * @run main StreamWitupTest
 */

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testGenerateUsesSupplier();
    }

    static void testNullSupplierThrowsNPE() {
        try {
            Stream.generate(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null supplier");
    }

    static void testGenerateUsesSupplier() {
        int[] counter = {0};
        Object[] result = Stream.generate(() -> ++counter[0]).limit(3).toArray();
        Object[] expected = new Object[] {1, 2, 3};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("Unexpected elements: " + Arrays.toString(result));
        }
    }
}
