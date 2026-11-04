/*
 * @test
 * @summary WITUP-generated tests for OptionalDouble.orElseThrow()
 * @run main OptionalDoubleWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testEmptyThrowsNoSuchElementException() {
        OptionalDouble empty = OptionalDouble.empty();
        try {
            empty.orElseThrow();
            throw new RuntimeException("Expected NoSuchElementException for empty OptionalDouble");
        } catch (NoSuchElementException expected) {
            check("No value present".equals(expected.getMessage()),
                  "Unexpected message: " + expected.getMessage());
        }
    }

    private static void testPresentReturnsValue() {
        check(OptionalDouble.of(3.5).orElseThrow() == 3.5, "orElseThrow should return 3.5");
        check(OptionalDouble.of(-0.0).orElseThrow() == 0.0, "orElseThrow should return -0.0");
        check(Double.compare(OptionalDouble.of(-0.0).orElseThrow(), -0.0) == 0,
              "orElseThrow should preserve negative zero");
    }

    public static void main(String[] args) {
        testEmptyThrowsNoSuchElementException();
        testPresentReturnsValue();
    }
}
