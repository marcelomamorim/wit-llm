/*
 * @test
 * @summary Witup-derived checks for Objects.checkIndex(int, int) bounds behavior
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        checkReturnsIndex(0, 1);
        checkReturnsIndex(4, 5);
        checkReturnsIndex(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);

        expectOutOfBounds(-1, 5);
        expectOutOfBounds(5, 5);
        expectOutOfBounds(6, 5);
        expectOutOfBounds(0, 0);
        expectOutOfBounds(-1, 0);
        expectOutOfBounds(Integer.MIN_VALUE, 1);
    }

    static void checkReturnsIndex(int index, int length) {
        int result = java.util.Objects.checkIndex(index, length);
        if (result != index) {
            throw new RuntimeException("Expected " + index + " but got " + result);
        }
    }

    static void expectOutOfBounds(int index, int length) {
        try {
            java.util.Objects.checkIndex(index, length);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("No IndexOutOfBoundsException for index=" + index + ", length=" + length);
    }
}
