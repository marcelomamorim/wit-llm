/* @test    @summary Test java.util.Objects.checkIndex method for valid and invalid inputs    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testCheckIndexValid();
        testCheckIndexNegative();
        testCheckIndexEqualLength();
        testCheckIndexExceedsLength();
    }

    static void testCheckIndexValid() {
        int index = 3;
        int length = 5;
        int result = Objects.checkIndex(index, length);
        if (result != index) {
            throw new AssertionError("Expected checkIndex to return the input index for valid input");
        }
    }

    static void testCheckIndexNegative() {
        int index = -1;
        int length = 5;
        try {
            Objects.checkIndex(index, length);
            throw new AssertionError("Expected checkIndex to throw IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckIndexEqualLength() {
        int index = 5;
        int length = 5;
        try {
            Objects.checkIndex(index, length);
            throw new AssertionError("Expected checkIndex to throw IndexOutOfBoundsException when index equals length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckIndexExceedsLength() {
        int index = 6;
        int length = 5;
        try {
            Objects.checkIndex(index, length);
            throw new AssertionError("Expected checkIndex to throw IndexOutOfBoundsException when index exceeds length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
