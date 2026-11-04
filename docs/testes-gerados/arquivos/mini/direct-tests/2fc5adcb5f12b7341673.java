/*
 * @test
 * @summary Test java.lang.Math.addExact(int,int) for correctness and overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testAddExactNormal();
        testAddExactOverflowPositive();
        testAddExactOverflowNegative();
    }

    static void testAddExactNormal() {
        // Normal addition without overflow
        int a = 1000;
        int b = 2000;
        int result = java.lang.Math.addExact(a, b);
        if (result != 3000) {
            throw new RuntimeException("addExact normal addition failed: expected 3000 but got " + result);
        }

        // Addition with zero
        if (java.lang.Math.addExact(0, 0) != 0) {
            throw new RuntimeException("addExact zero addition failed");
        }

        // Addition with negative and positive that does not overflow
        if (java.lang.Math.addExact(-500, 500) != 0) {
            throw new RuntimeException("addExact negative and positive addition failed");
        }
    }

    static void testAddExactOverflowPositive() {
        // Test positive overflow: Integer.MAX_VALUE + 1
        try {
            java.lang.Math.addExact(Integer.MAX_VALUE, 1);
            throw new RuntimeException("addExact did not throw ArithmeticException on positive overflow");
        } catch (ArithmeticException e) {
            if (!"integer overflow".equals(e.getMessage())) {
                throw new RuntimeException("addExact threw ArithmeticException with unexpected message: " + e.getMessage());
            }
        }
    }

    static void testAddExactOverflowNegative() {
        // Test negative overflow: Integer.MIN_VALUE + (-1)
        try {
            java.lang.Math.addExact(Integer.MIN_VALUE, -1);
            throw new RuntimeException("addExact did not throw ArithmeticException on negative overflow");
        } catch (ArithmeticException e) {
            if (!"integer overflow".equals(e.getMessage())) {
                throw new RuntimeException("addExact threw ArithmeticException with unexpected message: " + e.getMessage());
            }
        }
    }
}
