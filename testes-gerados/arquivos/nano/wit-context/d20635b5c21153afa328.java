/* @test    @summary Test subtractExact for overflow and normal cases    @run main MathWitupTest */

import java.lang.ArithmeticException;

public class MathWitupTest {

    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    static void testSubtractExactNormal() {
        int x = 1000;
        int y = 200;
        int expected = 800;
        int result = Math.subtractExact(x, y);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testSubtractExactOverflow() {
        int x = Integer.MAX_VALUE;
        int y = -1;
        try {
            Math.subtractExact(x, y);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
