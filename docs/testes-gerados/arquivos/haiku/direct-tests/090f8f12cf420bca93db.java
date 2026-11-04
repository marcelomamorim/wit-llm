/*
 * @test
 * @summary Tests Objects.checkFromToIndex returns the fromIndex for valid ranges and throws IndexOutOfBoundsException otherwise
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        int failures = 0;

        failures += checkReturn(0, 0, 0, 0);
        failures += checkReturn(0, 5, 5, 0);
        failures += checkReturn(2, 4, 5, 2);
        failures += checkReturn(5, 5, 5, 5);

        failures += checkOutOfBounds(-1, 1, 5);
        failures += checkOutOfBounds(0, 6, 5);
        failures += checkOutOfBounds(3, 2, 5);
        failures += checkOutOfBounds(0, 0, -1);
        failures += checkOutOfBounds(0, 1, 0);

        if (failures > 0) {
            throw new RuntimeException("Failures: " + failures);
        }
    }

    private static int checkReturn(int from, int to, int length, int expected) {
        int actual = Objects.checkFromToIndex(from, to, length);
        if (actual != expected) {
            System.err.println("Expected " + expected + " but got " + actual
                    + " for (" + from + ", " + to + ", " + length + ")");
            return 1;
        }
        return 0;
    }

    private static int checkOutOfBounds(int from, int to, int length) {
        try {
            Objects.checkFromToIndex(from, to, length);
            System.err.println("No exception for (" + from + ", " + to + ", " + length + ")");
            return 1;
        } catch (IndexOutOfBoundsException expected) {
            return 0;
        }
    }
}
