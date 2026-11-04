/*
 * @test
 * @summary Tests EnumSet.of(E e1, E e2) observable behavior: contents, duplicates, natural order and null rejection
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW }

    public static void main(String[] args) {
        testBasicContent();
        testSameElementTwice();
        testNaturalOrder();
        testNullFirstElementThrowsNPE();
        testNullSecondElementThrowsNPE();
    }

    static void testBasicContent() {
        EnumSet<Color> s = EnumSet.of(Color.BLUE, Color.RED);
        check(s.size() == 2, "size should be 2");
        check(s.contains(Color.RED), "RED should be present");
        check(s.contains(Color.BLUE), "BLUE should be present");
        check(!s.contains(Color.GREEN), "GREEN should not be present");
        check(!s.contains(Color.YELLOW), "YELLOW should not be present");
    }

    static void testSameElementTwice() {
        EnumSet<Color> s = EnumSet.of(Color.GREEN, Color.GREEN);
        check(s.size() == 1, "duplicate argument should yield size 1");
        check(s.contains(Color.GREEN), "GREEN should be present");
    }

    static void testNaturalOrder() {
        EnumSet<Color> s = EnumSet.of(Color.YELLOW, Color.RED);
        Iterator<Color> it = s.iterator();
        check(it.hasNext() && it.next() == Color.RED, "first element should be RED");
        check(it.hasNext() && it.next() == Color.YELLOW, "second element should be YELLOW");
        check(!it.hasNext(), "no further elements expected");
    }

    static void testNullFirstElementThrowsNPE() {
        boolean thrown = false;
        try {
            EnumSet.of((Color) null, Color.RED);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null first element should throw NullPointerException");
    }

    static void testNullSecondElementThrowsNPE() {
        boolean thrown = false;
        try {
            EnumSet.of(Color.RED, (Color) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null second element should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
