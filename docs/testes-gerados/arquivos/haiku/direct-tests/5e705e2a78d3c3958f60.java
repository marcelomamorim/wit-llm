/*
 * @test
 * @summary Verifies Objects.checkIndex returns valid indexes and throws IndexOutOfBoundsException for out-of-range values
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        check(Objects.checkIndex(0, 1) == 0, 0, 1);
        check(Objects.checkIndex(2, 3) == 2, 2, 3);
        check(Objects.checkIndex(4, 5) == 4, 4, 5);
        check(Objects.checkIndex(Integer.MAX_VALUE - 1, Integer.MAX_VALUE) == Integer.MAX_VALUE - 1,
              Integer.MAX_VALUE - 1, Integer.MAX_VALUE);

        expectIOOBE(1, 1);
        expectIOOBE(-1, 1);
        expectIOOBE(0, 0);
        expectIOOBE(-1, 0);
        expectIOOBE(5, 3);
        expectIOOBE(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    static void check(boolean condition, int index, int length) {
        if (!condition) {
            throw new AssertionError("Unexpected result for index=" + index + ", length=" + length);
        }
    }

    static void expectIOOBE(int index, int length) {
        try {
            Objects.checkIndex(index, length);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("Expected IndexOutOfBoundsException for index=" + index + ", length=" + length);
    }
}
