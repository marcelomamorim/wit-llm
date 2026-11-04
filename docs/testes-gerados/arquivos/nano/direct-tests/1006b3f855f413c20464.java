/* @test    @summary Test Objects.checkFromIndexSize with valid and invalid inputs    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testCheckFromIndexSizeValid();
        testCheckFromIndexSizeInvalid();
    }

    static void testCheckFromIndexSizeValid() {
        int fromIndex = 2;
        int size = 3;
        int length = 10;
        int result = Objects.checkFromIndexSize(fromIndex, size, length);
        if (result != fromIndex) {
            throw new AssertionError("Expected " + fromIndex + " but got " + result);
        }
    }

    static void testCheckFromIndexSizeInvalid() {
        int fromIndex = 8;
        int size = 3;
        int length = 10;
        try {
            Objects.checkFromIndexSize(fromIndex, size, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex=" + fromIndex + ", size=" + size + ", length=" + length);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        fromIndex = -1;
        size = 2;
        try {
            Objects.checkFromIndexSize(fromIndex, size, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex=" + fromIndex + ", size=" + size + ", length=" + length);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        fromIndex = 5;
        size = 6;
        length = 10;
        try {
            Objects.checkFromIndexSize(fromIndex, size, length);
            // should pass
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception for fromIndex=" + fromIndex + ", size=" + size + ", length=" + length);
        }
    }
}
