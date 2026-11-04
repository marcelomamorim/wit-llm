/*
 * @test
 * @summary Verifies EnumSet.noneOf for small and large enum types and for non-enum classes
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    enum Big { B00, B01, B02, B03, B04, B05, B06, B07, B08, B09, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31, B32, B33, B34, B35, B36, B37, B38, B39, B40, B41, B42, B43, B44, B45, B46, B47, B48, B49, B50, B51, B52, B53, B54, B55, B56, B57, B58, B59, B60, B61, B62, B63, B64 }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSmallEmpty() {
        EnumSet<Color> s = EnumSet.noneOf(Color.class);
        check(s != null, "noneOf returned null");
        check(s.isEmpty(), "noneOf(Color) should be empty");
        check(s.size() == 0, "size should be 0");
        check(!s.contains(Color.RED), "should not contain RED");
    }

    static void testSmallAddAndContains() {
        EnumSet<Color> s = EnumSet.noneOf(Color.class);
        check(s.add(Color.GREEN), "add should return true for new element");
        check(s.contains(Color.GREEN), "should contain GREEN after add");
        check(s.size() == 1, "size should be 1");
        check(!s.add(Color.GREEN), "duplicate add should return false");
        check(s.size() == 1, "size should remain 1");
    }

    static void testSetsAreIndependent() {
        EnumSet<Color> a = EnumSet.noneOf(Color.class);
        a.add(Color.BLUE);
        EnumSet<Color> b = EnumSet.noneOf(Color.class);
        check(b.isEmpty(), "second noneOf set should not share state with first");
        check(a.size() == 1, "first set should keep its element");
    }

    static void testJumboEmptyAndAdd() {
        EnumSet<Big> s = EnumSet.noneOf(Big.class);
        check(s.isEmpty(), "noneOf(Big) should be empty");
        check(s.add(Big.B64), "add of last constant should succeed");
        check(s.contains(Big.B64), "should contain B64");
        check(s.add(Big.B00), "add of first constant should succeed");
        check(s.size() == 2, "size should be 2");
        check(!s.contains(Big.B32), "should not contain B32");
    }

    static void testNonEnumClassThrows() {
        boolean thrown = false;
        try {
            EnumSet.noneOf((Class) String.class);
        } catch (ClassCastException e) {
            thrown = true;
            check(e.getMessage() != null && e.getMessage().contains("not an enum"),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "noneOf(String.class) should throw ClassCastException");
    }

    public static void main(String[] args) {
        testSmallEmpty();
        testSmallAddAndContains();
        testSetsAreIndependent();
        testJumboEmptyAndAdd();
        testNonEnumClassThrows();
    }
}
