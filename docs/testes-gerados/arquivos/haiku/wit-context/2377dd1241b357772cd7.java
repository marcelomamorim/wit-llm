/*
 * @test
 * @summary Witup-derived checks for Math.decrementExact(long) overflow and boundary behavior
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.decrementExact(0L) == -1L, "decrementExact(0) deve retornar -1");
        check(Math.decrementExact(1L) == 0L, "decrementExact(1) deve retornar 0");
        check(Math.decrementExact(-1L) == -2L, "decrementExact(-1) deve retornar -2");
        check(Math.decrementExact(Long.MIN_VALUE + 1L) == Long.MIN_VALUE, "limite inferior sem overflow");
        check(Math.decrementExact(Long.MAX_VALUE) == Long.MAX_VALUE - 1L, "decrementExact(MAX_VALUE) deve retornar MAX_VALUE - 1");

        boolean thrown = false;
        try {
            Math.decrementExact(Long.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "decrementExact(Long.MIN_VALUE) deve lancar ArithmeticException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
