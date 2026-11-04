/*
 * @test
 * @summary Witup-generated tests for Math.subtractExact(int,int) overflow behavior
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        checkValue(0, 0, 0);
        checkValue(10, 4, 6);
        checkValue(-5, -8, 3);
        checkValue(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        checkValue(Integer.MIN_VALUE, 0, Integer.MIN_VALUE);
        checkValue(Integer.MIN_VALUE, -1, Integer.MIN_VALUE + 1);
        checkValue(-1, Integer.MAX_VALUE, Integer.MIN_VALUE);

        checkOverflow(Integer.MAX_VALUE, -1);
        checkOverflow(Integer.MIN_VALUE, 1);
        checkOverflow(0, Integer.MIN_VALUE);
        checkOverflow(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    static void checkValue(int x, int y, int expected) {
        int actual = Math.subtractExact(x, y);
        if (actual != expected) {
            throw new RuntimeException("subtractExact(" + x + ", " + y + ") returned "
                    + actual + ", expected " + expected);
        }
    }

    static void checkOverflow(int x, int y) {
        try {
            int r = Math.subtractExact(x, y);
            throw new RuntimeException("subtractExact(" + x + ", " + y
                    + ") did not throw ArithmeticException, returned " + r);
        } catch (ArithmeticException e) {
            if (!"integer overflow".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
