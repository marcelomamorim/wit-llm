/*
 * @test
 * @summary Tests Arrays.copyOfRange(U[], int, int, Class<? extends T[]>) range validation and observable results
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testInvalidRangeThrowsIllegalArgumentException();
        testEqualBoundsReturnsEmptyArray();
        testSubRangeCopy();
        testPaddingBeyondOriginalLength();
        testObjectArrayClassReturnsObjectArray();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInvalidRangeThrowsIllegalArgumentException() {
        String[] src = {"a", "b", "c"};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(src, 3, 1, String[].class);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when from > to");
    }

    static void testEqualBoundsReturnsEmptyArray() {
        String[] src = {"a", "b", "c"};
        String[] r = Arrays.copyOfRange(src, 2, 2, String[].class);
        check(r.length == 0, "expected empty array when from == to");
    }

    static void testSubRangeCopy() {
        String[] src = {"a", "b", "c"};
        String[] r = Arrays.copyOfRange(src, 1, 3, String[].class);
        check(r.length == 2, "unexpected length for sub-range");
        check("b".equals(r[0]) && "c".equals(r[1]), "unexpected content for sub-range");
    }

    static void testPaddingBeyondOriginalLength() {
        String[] src = {"a", "b", "c"};
        String[] r = Arrays.copyOfRange(src, 1, 5, String[].class);
        check(r.length == 4, "unexpected padded length");
        check("b".equals(r[0]) && "c".equals(r[1]), "unexpected copied prefix");
        check(r[2] == null && r[3] == null, "expected null padding beyond original length");
    }

    static void testObjectArrayClassReturnsObjectArray() {
        String[] src = {"a", "b", "c"};
        Object[] r = Arrays.copyOfRange(src, 0, 2, Object[].class);
        check(r.getClass() == Object[].class, "expected Object[] runtime type");
        check(r.length == 2 && "a".equals(r[0]) && "b".equals(r[1]), "unexpected content for Object[] copy");
    }
}
