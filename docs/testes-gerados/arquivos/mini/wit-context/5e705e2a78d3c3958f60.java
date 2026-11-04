/*
 * @test
 * @summary Test java.util.Objects.checkIndex(int, int) for bounds checking
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testValidIndex();
        testIndexNegative();
        testIndexEqualLength();
        testIndexGreaterThanLength();
        System.out.println("All tests passed.");
    }

    private static void testValidIndex() {
        // index within bounds should return index unchanged
        int length = 5;
        for (int i = 0; i < length; i++) {
            int result = java.util.Objects.checkIndex(i, length);
            if (result != i) {
                throw new AssertionError("checkIndex returned " + result + " for valid index " + i);
            }
        }
    }

    private static void testIndexNegative() {
        int length = 5;
        try {
            java.util.Objects.checkIndex(-1, length);
            throw new AssertionError("checkIndex did not throw for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testIndexEqualLength() {
        int length = 5;
        try {
            java.util.Objects.checkIndex(length, length);
            throw new AssertionError("checkIndex did not throw for index equal to length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testIndexGreaterThanLength() {
        int length = 5;
        try {
            java.util.Objects.checkIndex(length + 1, length);
            throw new AssertionError("checkIndex did not throw for index greater than length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
