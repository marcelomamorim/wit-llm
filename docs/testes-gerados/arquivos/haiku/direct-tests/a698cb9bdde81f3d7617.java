/*
 * @test
 * @summary Verifies OptionalDouble.getAsDouble returns the stored value or throws NoSuchElementException when empty
 * @run main OptionalDoubleWitupTest
 */
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        checkPresentValue();
        checkNegativeZeroPreserved();
        checkNaNPreserved();
        checkEmptyThrows();
    }

    static void checkPresentValue() {
        double actual = OptionalDouble.of(3.5).getAsDouble();
        if (actual != 3.5) {
            throw new RuntimeException("Expected 3.5 but got " + actual);
        }
    }

    static void checkNegativeZeroPreserved() {
        double actual = OptionalDouble.of(-0.0).getAsDouble();
        if (Double.doubleToRawLongBits(actual) != Double.doubleToRawLongBits(-0.0)) {
            throw new RuntimeException("Negative zero not preserved");
        }
    }

    static void checkNaNPreserved() {
        double actual = OptionalDouble.of(Double.NaN).getAsDouble();
        if (!Double.isNaN(actual)) {
            throw new RuntimeException("Expected NaN but got " + actual);
        }
    }

    static void checkEmptyThrows() {
        try {
            OptionalDouble.empty().getAsDouble();
        } catch (NoSuchElementException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException for empty OptionalDouble");
    }
}
