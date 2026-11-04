/*
 * @test
 * @summary Verifies EnumSet.of(E, E, E) contents, duplicate handling, natural ordering and null rejection
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW }

    public static void main(String[] args) {
        testBasicContents();
        testDuplicatesCollapse();
        testNaturalOrder();
        testNullFirstElementRejected();
        testNullSecondElementRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicContents() {
        EnumSet<Color> s = EnumSet.of(Color.BLUE, Color.RED, Color.GREEN);
        check(s.size() == 3, "expected size 3 but was " + s.size());
        check(s.contains(Color.RED), "RED missing");
        check(s.contains(Color.GREEN), "GREEN missing");
        check(s.contains(Color.BLUE), "BLUE missing");
        check(!s.contains(Color.YELLOW), "YELLOW should not be present");
    }

    static void testDuplicatesCollapse() {
        EnumSet<Color> s = EnumSet.of(Color.RED, Color.RED, Color.BLUE);
        check(s.size() == 2, "duplicates should collapse, size was " + s.size());
        check(s.contains(Color.RED), "RED missing");
        check(s.contains(Color.BLUE), "BLUE missing");
    }

    static void testNaturalOrder() {
        EnumSet<Color> s = EnumSet.of(Color.BLUE, Color.RED, Color.GREEN);
        Iterator<Color> it = s.iterator();
        check(it.hasNext() && it.next() == Color.RED, "first element should be RED");
        check(it.hasNext() && it.next() == Color.GREEN, "second element should be GREEN");
        check(it.hasNext() && it.next() == Color.BLUE, "third element should be BLUE");
        check(!it.hasNext(), "unexpected extra element");
    }

    static void testNullFirstElementRejected() {
        boolean thrown = false;
        try {
            EnumSet.of(null, Color.GREEN, Color.BLUE);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null e1 should cause NullPointerException");
    }

    static void testNullSecondElementRejected() {
        boolean thrown = false;
        try {
            EnumSet.of(Color.RED, null, Color.BLUE);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null e2 should cause NullPointerException");
    }
}
