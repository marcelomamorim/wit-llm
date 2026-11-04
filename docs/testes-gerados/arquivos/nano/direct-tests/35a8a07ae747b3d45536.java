/*
 * @test
 * @summary Test for OptionalDouble.orElseThrow() method behavior.
 * @run main OptionalDoubleWitupTest
 */
import java.util.OptionalDouble;
import java.util.NoSuchElementException;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.orElseThrow();
        if (result != 3.14) {
            throw new AssertionError("Expected 3.14, got " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        OptionalDouble opt = OptionalDouble.empty();
        try {
            opt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
