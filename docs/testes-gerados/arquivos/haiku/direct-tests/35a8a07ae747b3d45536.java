/*
 * @test
 * @summary Verifies OptionalDouble.orElseThrow() returns the contained value or throws NoSuchElementException when empty
 * @run main OptionalDoubleWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testPresentValueIsReturned();
        testNegativeAndZeroValues();
        testEmptyThrowsNoSuchElementException();
        testEmptyMessage();
    }

    static void testPresentValueIsReturned() {
        double expected = 3.5;
        double actual = OptionalDouble.of(expected).orElseThrow();
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void testNegativeAndZeroValues() {
        double zero = OptionalDouble.of(0.0).orElseThrow();
        if (zero != 0.0) {
            throw new RuntimeException("Expected 0.0 but got " + zero);
        }
        double negative = OptionalDouble.of(-42.25).orElseThrow();
        if (negative != -42.25) {
            throw new RuntimeException("Expected -42.25 but got " + negative);
        }
    }

    static void testEmptyThrowsNoSuchElementException() {
        boolean thrown = false;
        try {
            OptionalDouble.empty().orElseThrow();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException for empty OptionalDouble");
        }
    }

    static void testEmptyMessage() {
        try {
            OptionalDouble.empty().orElseThrow();
            throw new RuntimeException("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
