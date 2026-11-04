/*
 * @test
 * @summary Test Arrays.copyOfRange for various array types and bounds
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeObjectArray();
        testCopyOfRangeIntArray();
        testCopyOfRangeEmptyArray();
        testCopyOfRangeFromZero();
        testCopyOfRangeToZero();
        testCopyOfRangeInvalidArguments();
    }

    static void testCopyOfRangeObjectArray() {
        String[] original = {"a", "b", "c", "d"};
        String[] result = Arrays.copyOfRange(original, 1, 3, String[].class);
        assert result.length == 2 : "Length mismatch";
        assert "b" .equals(result[0]) : "First element mismatch";
        assert "c" .equals(result[1]) : "Second element mismatch";
    }

    static void testCopyOfRangeIntArray() {
        int[] original = {1, 2, 3, 4, 5};
        int[] result = Arrays.copyOfRange(original, 2, 4, int[].class);
        assert result.length == 2 : "Length mismatch";
        assert result[0] == 3 : "First element mismatch";
        assert result[1] == 4 : "Second element mismatch";
    }

    static void testCopyOfRangeEmptyArray() {
        String[] original = {};
        String[] result = Arrays.copyOfRange(original, 0, 0, String[].class);
        assert result.length == 0 : "Result should be empty";
    }

    static void testCopyOfRangeFromZero() {
        String[] original = {"x", "y"};
        String[] result = Arrays.copyOfRange(original, 0, 2, String[].class);
        assert result.length == 2 : "Length mismatch";
        assert "x" .equals(result[0]) : "First element mismatch";
        assert "y" .equals(result[1]) : "Second element mismatch";
    }

    static void testCopyOfRangeToZero() {
        String[] original = {"a", "b"};
        String[] result = Arrays.copyOfRange(original, 0, 0, String[].class);
        assert result.length == 0 : "Result should be empty";
    }

    static void testCopyOfRangeInvalidArguments() {
        String[] original = {"a"};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, 2, 1, String[].class);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert thrown : "Expected IllegalArgumentException for from > to";
    }
}
