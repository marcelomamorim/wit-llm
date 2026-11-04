/*
 * @test
 * @summary Witup-derived checks for Arrays.copyOfRange(T[], int, int): copy contents, runtime type, padding, and invalid range handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        String[] src = {"a", "b", "c", "d"};

        String[] r = Arrays.copyOfRange(src, 1, 3);
        check(r.length == 2, "length of range copy");
        check("b".equals(r[0]) && "c".equals(r[1]), "contents of range copy");
        check(r.getClass() == String[].class, "runtime type preserved");
        check(r != src, "result is a new array");

        Integer[] empty = Arrays.copyOfRange(new Integer[]{1, 2}, 1, 1);
        check(empty.length == 0, "from == to yields empty array");
        check(empty.getClass() == Integer[].class, "empty copy keeps runtime type");

        String[] padded = Arrays.copyOfRange(src, 2, 6);
        check(padded.length == 4, "to beyond length pads result");
        check("c".equals(padded[0]) && "d".equals(padded[1]), "copied elements before padding");
        check(padded[2] == null && padded[3] == null, "padding is null");

        expectIllegalArgument(src, 3, 1);

        expectArrayIndexOutOfBounds(src, -1, 2);

        expectNullPointer();
    }

    private static void expectIllegalArgument(String[] src, int from, int to) {
        try {
            Arrays.copyOfRange(src, from, to);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for from > to");
    }

    private static void expectArrayIndexOutOfBounds(String[] src, int from, int to) {
        try {
            Arrays.copyOfRange(src, from, to);
        } catch (ArrayIndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("ArrayIndexOutOfBoundsException expected for negative from");
    }

    private static void expectNullPointer() {
        try {
            String[] nothing = null;
            Arrays.copyOfRange(nothing, 0, 0);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null array");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
