/*
 * @test
 * @summary Tests IntStream.generate(IntSupplier) for null rejection, element values and laziness
 * @run main IntStreamWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testNullSupplierThrowsNullPointerException();
        testGeneratedElementsComeFromSupplier();
        testSupplierIsNotInvokedUntilTerminalOperation();
    }

    static void testNullSupplierThrowsNullPointerException() {
        boolean thrown = false;
        try {
            IntStream.generate(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null supplier");
        }
    }

    static void testGeneratedElementsComeFromSupplier() {
        AtomicInteger counter = new AtomicInteger();
        int[] actual = IntStream.generate(counter::incrementAndGet).limit(3).toArray();
        int[] expected = {1, 2, 3};
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected elements: " + Arrays.toString(actual));
        }
    }

    static void testSupplierIsNotInvokedUntilTerminalOperation() {
        AtomicInteger calls = new AtomicInteger();
        IntStream stream = IntStream.generate(() -> calls.incrementAndGet());
        if (calls.get() != 0) {
            throw new RuntimeException("Supplier invoked eagerly: " + calls.get());
        }
        int[] actual = stream.limit(2).toArray();
        int[] expected = {1, 2};
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected elements: " + Arrays.toString(actual));
        }
    }
}
