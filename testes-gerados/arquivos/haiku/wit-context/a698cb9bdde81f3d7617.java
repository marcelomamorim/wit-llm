/*
 * @test
 * @summary Tests OptionalDouble.getAsDouble() behavior for empty and present values
 * @run main OptionalDoubleWitupTest
 */

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testEmptyThrowsNoSuchElementException();
        testPresentValueIsReturned();
        testPresentZeroIsReturned();
        testPresentNegativeValueIsReturned();
    }

    static void testEmptyThrowsNoSuchElementException() {
        OptionalDouble empty = OptionalDouble.empty();
        try {
            empty.getAsDouble();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            if (!"No value present".equals(expected.getMessage())) {
                throw new AssertionError("Unexpected message: " + expected.getMessage());
            }
        }
    }

    static void testPresentValueIsReturned() {
        OptionalDouble od = OptionalDouble.of(3.5);
        if (od.getAsDouble() != 3.5) {
            throw new AssertionError("Expected 3.5");
        }
    }

    static void testPresentZeroIsReturned() {
        OptionalDouble od = OptionalDouble.of(0.0);
        if (od.getAsDouble() != 0.0) {
            throw new AssertionError("Expected 0.0");
        }
    }

    static void testPresentNegativeValueIsReturned() {
        OptionalDouble od = OptionalDouble.of(-7.25);
        if (od.getAsDouble() != -7.25) {
            throw new AssertionError("Expected -7.25");
        }
    }
}
