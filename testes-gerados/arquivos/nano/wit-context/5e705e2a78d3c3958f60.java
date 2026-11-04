/* @test    @summary Test for java.util.Objects.checkIndex(int, int) to verify index bounds checking.    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testCheckIndexValid();
        testCheckIndexNegative();
        testCheckIndexExceeds();
    }

    private static void testCheckIndexValid() {
        int index = 2;
        int length = 5;
        int result = Objects.checkIndex(index, length);
        if (result != index) {
            throw new AssertionError("Expected checkIndex to return the input index when valid.");
        }
    }

    private static void testCheckIndexNegative() {
        int index = -1;
        int length = 5;
        try {
            Objects.checkIndex(index, length);
            throw new AssertionError("Expected checkIndex to throw IndexOutOfBoundsException for negative index.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testCheckIndexExceeds() {
        int index = 5;
        int length = 5;
        try {
            Objects.checkIndex(index, length);
            throw new AssertionError("Expected checkIndex to throw IndexOutOfBoundsException when index equals length.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
