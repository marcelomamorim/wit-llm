/*
 * @test
 * @summary Test Math.subtractExact(int,int) for correct results and overflow exception
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
        System.out.println("MathWitupTest passed.");
    }

    private static void testSubtractExactNormal() {
        // Test normal subtraction without overflow
        int[][] cases = {
            {10, 5, 5},
            {0, 0, 0},
            {-10, -5, -5},
            {Integer.MAX_VALUE, 1, Integer.MAX_VALUE - 1},
            {Integer.MIN_VALUE + 1, 1, Integer.MIN_VALUE}
        };
        for (int[] c : cases) {
            int x = c[0];
            int y = c[1];
            int expected = c[2];
            int result = Math.subtractExact(x, y);
            if (result != expected) {
                throw new AssertionError("subtractExact(" + x + ", " + y + ") = " + result + ", expected " + expected);
            }
        }
    }

    private static void testSubtractExactOverflow() {
        // Test cases that cause overflow and must throw ArithmeticException
        int[][] overflowCases = {
            {Integer.MIN_VALUE, 1}, // MIN_VALUE - 1 overflows
            {Integer.MAX_VALUE, -1} // MAX_VALUE - (-1) = MAX_VALUE + 1 overflows
        };
        for (int[] c : overflowCases) {
            int x = c[0];
            int y = c[1];
            boolean thrown = false;
            try {
                Math.subtractExact(x, y);
            } catch (ArithmeticException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected ArithmeticException for subtractExact(" + x + ", " + y + ")");
            }
        }
    }
}
