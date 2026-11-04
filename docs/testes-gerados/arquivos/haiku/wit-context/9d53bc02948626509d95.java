/*
 * @test
 * @summary Behavioral tests for EnumSet.of(E e): single-element result, mutability, null rejection, and enum types with more than 64 constants
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    enum Op { PLUS, MINUS }

    enum Big {
        B00, B01, B02, B03, B04, B05, B06, B07, B08, B09,
        B10, B11, B12, B13, B14, B15, B16, B17, B18, B19,
        B20, B21, B22, B23, B24, B25, B26, B27, B28, B29,
        B30, B31, B32, B33, B34, B35, B36, B37, B38, B39,
        B40, B41, B42, B43, B44, B45, B46, B47, B48, B49,
        B50, B51, B52, B53, B54, B55, B56, B57, B58, B59,
        B60, B61, B62, B63, B64
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleElementContent() {
        EnumSet<Color> s = EnumSet.of(Color.GREEN);
        check(s.size() == 1, "size must be 1");
        check(s.contains(Color.GREEN), "must contain GREEN");
        check(!s.contains(Color.RED), "must not contain RED");
        check(!s.contains(Color.BLUE), "must not contain BLUE");
        check(!s.isEmpty(), "must not be empty");
    }

    static void testResultIsDistinctAndMutable() {
        EnumSet<Color> a = EnumSet.of(Color.RED);
        EnumSet<Color> b = EnumSet.of(Color.RED);
        check(a != b, "each call must return a new set");
        check(a.equals(b), "sets with the same element must be equal");
        check(a.add(Color.BLUE), "add of a new element must succeed");
        check(a.size() == 2, "size must be 2 after add");
        check(b.size() == 1, "other set must not be affected");
    }

    static void testEnumWithMultipleConstants() {
        Set<Op> s = EnumSet.of(Op.MINUS);
        check(s.size() == 1, "size must be 1 for Op");
        check(s.contains(Op.MINUS), "must contain MINUS");
        check(!s.contains(Op.PLUS), "must not contain PLUS");
    }

    static void testNullElementRejected() {
        boolean thrown = false;
        try {
            EnumSet.of((Color) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null element must throw NullPointerException");
    }

    static void testLargeUniverse() {
        EnumSet<Big> s = EnumSet.of(Big.B64);
        check(s.size() == 1, "size must be 1 for large universe");
        check(s.contains(Big.B64), "must contain B64");
        check(!s.contains(Big.B00), "must not contain B00");
        check(s.add(Big.B00), "add must succeed on large universe set");
        check(s.size() == 2, "size must be 2 after add");
        Iterator<Big> it = s.iterator();
        check(it.next() == Big.B00, "iteration must follow declaration order");
        check(it.next() == Big.B64, "second element must be B64");
    }

    public static void main(String[] args) {
        testSingleElementContent();
        testResultIsDistinctAndMutable();
        testEnumWithMultipleConstants();
        testNullElementRejected();
        testLargeUniverse();
    }
}
