/* @test    @summary Test for String.copyValueOf with invalid bounds to trigger StringIndexOutOfBoundsException    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCopyValueOfWithInvalidBounds();
    }

    public static void testCopyValueOfWithInvalidBounds() {
        char[] data = {'a', 'b', 'c'};
        int offset = -1;
        int count = 2;
        try {
            String.copyValueOf(data, offset, count);
            throw new AssertionError("Expected StringIndexOutOfBoundsException was not thrown for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        offset = 1;
        count = 5;
        try {
            String.copyValueOf(data, offset, count);
            throw new AssertionError("Expected StringIndexOutOfBoundsException was not thrown for count exceeding array bounds");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        offset = 2;
        count = 1;
        try {
            String.copyValueOf(data, offset, count);
        } catch (StringIndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected StringIndexOutOfBoundsException for valid bounds", e);
        }
        offset = 0;
        count = 3;
        try {
            String.copyValueOf(data, offset, count);
        } catch (StringIndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected StringIndexOutOfBoundsException for valid bounds", e);
        }
    }
}
