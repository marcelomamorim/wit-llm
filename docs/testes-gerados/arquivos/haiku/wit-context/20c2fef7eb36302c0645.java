/*
 * @test
 * @summary EnumSet.of(e1, e2, e3, e4) returns a set with the four elements, handles duplicates, rejects null elements and works for universes larger than 64 constants
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum Small { A, B, C, D, E }

    enum Big { X0, X1, X2, X3, X4, X5, X6, X7, X8, X9, X10, X11, X12, X13, X14, X15, X16, X17, X18, X19, X20, X21, X22, X23, X24, X25, X26, X27, X28, X29, X30, X31, X32, X33, X34, X35, X36, X37, X38, X39, X40, X41, X42, X43, X44, X45, X46, X47, X48, X49, X50, X51, X52, X53, X54, X55, X56, X57, X58, X59, X60, X61, X62, X63, X64 }

    public static void main(String[] args) {
        testBasic();
        testDuplicates();
        testNullFirstElement();
        testNullFourthElement();
        testLargeUniverse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasic() {
        EnumSet<Small> s = EnumSet.of(Small.D, Small.B, Small.A, Small.C);
        check(s.size() == 4, "size expected 4 but was " + s.size());
        check(s.contains(Small.A), "missing A");
        check(s.contains(Small.B), "missing B");
        check(s.contains(Small.C), "missing C");
        check(s.contains(Small.D), "missing D");
        check(!s.contains(Small.E), "E must not be present");
        check(s.toString().equals("[A, B, C, D]"), "unexpected order/content: " + s);
    }

    static void testDuplicates() {
        EnumSet<Small> s = EnumSet.of(Small.A, Small.A, Small.B, Small.B);
        check(s.size() == 2, "duplicates must collapse, size was " + s.size());
        check(s.contains(Small.A) && s.contains(Small.B), "duplicate case lost elements");
    }

    static void testNullFirstElement() {
        boolean thrown = false;
        try {
            EnumSet.of((Small) null, Small.B, Small.C, Small.D);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null e1 must throw NullPointerException");
    }

    static void testNullFourthElement() {
        boolean thrown = false;
        try {
            EnumSet.of(Small.A, Small.B, Small.C, (Small) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null e4 must throw NullPointerException");
    }

    static void testLargeUniverse() {
        EnumSet<Big> s = EnumSet.of(Big.X64, Big.X0, Big.X63, Big.X1);
        check(s.size() == 4, "large universe size expected 4 but was " + s.size());
        check(s.contains(Big.X64), "missing X64");
        check(s.contains(Big.X0), "missing X0");
        check(s.contains(Big.X1), "missing X1");
        check(s.contains(Big.X63), "missing X63");
        check(!s.contains(Big.X2), "X2 must not be present");
        check(s.toString().equals("[X0, X1, X63, X64]"), "unexpected content: " + s);
    }
}
