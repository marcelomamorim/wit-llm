/*
 * @test
 * @summary Checks Objects.checkFromIndexSize returns fromIndex for valid ranges and throws IndexOutOfBoundsException for out-of-bounds ranges
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        check(0, 0, 0, 0);
        check(2, 3, 5, 2);
        check(5, 0, 5, 5);
        check(0, 5, 5, 0);

        expectOutOfBounds(-1, 0, 5);
        expectOutOfBounds(0, -1, 5);
        expectOutOfBounds(3, 3, 5);
        expectOutOfBounds(0, 6, 5);
        expectOutOfBounds(Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
        expectOutOfBounds(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    static void check(int fromIndex, int size, int length, int expected) {
        int result = Objects.checkFromIndexSize(fromIndex, size, length);
        if (result != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + result
                    + " for (" + fromIndex + ", " + size + ", " + length + ")");
        }
    }

    static void expectOutOfBounds(int fromIndex, int size, int length) {
        try {
            Objects.checkFromIndexSize(fromIndex, size, length);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("No IndexOutOfBoundsException for (" + fromIndex + ", "
                + size + ", " + length + ")");
    }
}
