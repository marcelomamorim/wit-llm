/*
 * @test
 * @summary Verifies public Objects.checkFromToIndex returns fromIndex for valid ranges and throws IndexOutOfBoundsException for invalid ranges
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        check(Objects.checkFromToIndex(0, 0, 0), 0);
        check(Objects.checkFromToIndex(0, 3, 3), 0);
        check(Objects.checkFromToIndex(1, 2, 3), 1);
        check(Objects.checkFromToIndex(2, 2, 3), 2);
        check(Objects.checkFromToIndex(3, 3, 3), 3);

        expectIOOBE(-1, 0, 3);
        expectIOOBE(2, 1, 3);
        expectIOOBE(0, 4, 3);
        expectIOOBE(0, 0, -1);
        expectIOOBE(4, 4, 3);
        expectIOOBE(-1, -1, 0);
    }

    private static void check(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    private static void expectIOOBE(int from, int to, int length) {
        try {
            int result = Objects.checkFromToIndex(from, to, length);
            throw new RuntimeException("No exception for from=" + from + " to=" + to
                    + " length=" + length + " result=" + result);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
