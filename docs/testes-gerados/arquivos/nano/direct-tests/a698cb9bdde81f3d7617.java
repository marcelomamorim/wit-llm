/*
 * @test
 * @summary Test getAsDouble() method of OptionalDouble for present and absent cases
 * @run main OptionalDoubleWitupTest
 */
import java.util.OptionalDouble;
import java.util.NoSuchElementException;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testGetAsDoubleWithValue();
        testGetAsDoubleWithoutValue();
    }

    static void testGetAsDoubleWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.getAsDouble();
        if (result != 3.14) {
            throw new AssertionError("Expected 3.14, but got " + result);
        }
    }

    static void testGetAsDoubleWithoutValue() {
        OptionalDouble opt = OptionalDouble.empty();
        try {
            opt.getAsDouble();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
