/* @test    @summary Test subtractExact method for normal and overflow cases    @run main MathWitupTest_P96eae1befbf6 */

public class MathWitupTest_P96eae1befbf6 {

    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    static void testSubtractExactNormal() {
        long x = 100L;
        long y = 50L;
        long expected = 50L;
        long result = Math.subtractExact(x, y);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testSubtractExactOverflow() {
        long x = Long.MAX_VALUE;
        long y = -1L;
        try {
            Math.subtractExact(x, y);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            if (!e.getMessage().equals("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
