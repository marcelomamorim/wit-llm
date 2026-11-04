/* @test    @summary Test for Objects.checkFromToIndex method to verify index bounds checking    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testValidIndices();
        testInvalidFromIndex();
        testInvalidToIndex();
        testFromIndexEqualsToIndex();
        testFromIndexGreaterThanToIndex();
        testNegativeIndices();
    }

    static void testValidIndices() {
        int length = 10;
        int fromIndex = 2;
        int toIndex = 5;
        int result = Objects.checkFromToIndex(fromIndex, toIndex, length);
        assert result == fromIndex : "Expected fromIndex to be returned";
    }

    static void testInvalidFromIndex() {
        int length = 10;
        int fromIndex = -1;
        int toIndex = 5;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            assert false : "Expected IndexOutOfBoundsException for negative fromIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInvalidToIndex() {
        int length = 10;
        int fromIndex = 2;
        int toIndex = 11;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            assert false : "Expected IndexOutOfBoundsException for toIndex > length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFromIndexEqualsToIndex() {
        int length = 10;
        int fromIndex = 4;
        int toIndex = 4;
        int result = Objects.checkFromToIndex(fromIndex, toIndex, length);
        assert result == fromIndex : "Expected fromIndex to be returned when fromIndex == toIndex";
    }

    static void testFromIndexGreaterThanToIndex() {
        int length = 10;
        int fromIndex = 6;
        int toIndex = 4;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            assert false : "Expected IndexOutOfBoundsException when fromIndex > toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNegativeIndices() {
        int length = 10;
        int fromIndex = -3;
        int toIndex = 2;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            assert false : "Expected IndexOutOfBoundsException for negative fromIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        fromIndex = 2;
        toIndex = -2;
        try {
            Objects.checkFromToIndex(fromIndex, toIndex, length);
            assert false : "Expected IndexOutOfBoundsException for negative toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
