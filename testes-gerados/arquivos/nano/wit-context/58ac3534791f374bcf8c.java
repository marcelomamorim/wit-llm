/*
 * @test
 * @summary Test for IntStream.generate(IntSupplier s) method, focusing on null input handling.
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntSupplier;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
    }

    private static void testGenerateWithNullSupplier() {
        try {
            IntStream.generate(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null supplier.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }

    private static void testGenerateWithValidSupplier() {
        IntSupplier supplier = new IntSupplier() {
            private int current = 0;
            @Override
            public int getAsInt() {
                return current++;
            }
        };
        IntStream stream = IntStream.generate(supplier);
        int[] expected = {0, 1, 2, 3, 4};
        int index = 0;
        try {
            for (int val : stream.limit(5).toArray()) {
                if (val != expected[index]) {
                    System.err.println("Mismatch at index " + index + ": expected " + expected[index] + ", got " + val);
                    System.exit(1);
                }
                index++;
            }
            System.out.println("generate with valid supplier produces expected sequence.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception during valid supplier test: " + t);
            System.exit(1);
        }
    }
}
