/* @test    @summary Test for Objects.checkFromToIndex to verify index bounds checking behavior    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testCheckFromToIndexValid();
        testCheckFromToIndexFromIndexGreaterThanToIndex();
        testCheckFromToIndexFromIndexNegative();
        testCheckFromToIndexLengthLessThanToIndex();
    }

    static void testCheckFromToIndexValid() {
        int fromIndex = 1;
        int toIndex = 3;
        int length = 5;
        int result = Objects.checkFromToIndex(fromIndex, toIndex, length);
        if (result != toIndex) {
            throw new AssertionError("Expected result to be " + toIndex + " but was " + result);
        }
    }

    static void testCheckFromToIndexFromIndexGreaterThanToIndex() {
        int fromIndex = 4;
        int toIndex = 2;
        int length = 5;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex > toIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckFromToIndexFromIndexNegative() {
        int fromIndex = -1;
        int toIndex = 2;
        int length = 5;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCheckFromToIndexLengthLessThanToIndex() {
        int fromIndex = 1;
        int toIndex = 6;
        int length = 5;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
