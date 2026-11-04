/*
 * @test
 * @summary Verify Math.addExact(int,int) returns the exact sum and throws ArithmeticException on int overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        expectSum(0, 0, 0);
        expectSum(1, 2, 3);
        expectSum(-5, 3, -2);
        expectSum(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        expectSum(Integer.MIN_VALUE, 0, Integer.MIN_VALUE);
        expectSum(Integer.MAX_VALUE, Integer.MIN_VALUE, -1);
        expectSum(Integer.MIN_VALUE, Integer.MAX_VALUE, -1);
        expectSum(Integer.MAX_VALUE, -1, Integer.MAX_VALUE - 1);

        expectOverflow(Integer.MAX_VALUE, 1);
        expectOverflow(1, Integer.MAX_VALUE);
        expectOverflow(Integer.MAX_VALUE, Integer.MAX_VALUE);
        expectOverflow(Integer.MIN_VALUE, -1);
        expectOverflow(-1, Integer.MIN_VALUE);
        expectOverflow(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    static void expectSum(int x, int y, int expected) {
        int actual = Math.addExact(x, y);
        if (actual != expected) {
            throw new RuntimeException("addExact(" + x + ", " + y + ") returned " + actual
                    + " expected " + expected);
        }
    }

    static void expectOverflow(int x, int y) {
        try {
            int r = Math.addExact(x, y);
            throw new RuntimeException("no ArithmeticException for addExact(" + x + ", " + y
                    + ") returned " + r);
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
