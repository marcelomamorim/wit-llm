/*
 * @test
 * @summary Verifies EnumSet.of(E e1, E e2) contents, natural ordering, duplicate handling, fresh instances and null rejection
 * @run main EnumSetWitupTest
 */
import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {
    enum Color { RED, GREEN, BLUE, YELLOW }

    public static void main(String[] args) {
        testDistinctElements();
        testSameElementTwice();
        testNaturalOrder();
        testFreshInstance();
        testNullFirstElement();
        testNullSecondElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDistinctElements() {
        EnumSet<Color> s = EnumSet.of(Color.BLUE, Color.RED);
        check(s.size() == 2, "expected size 2 but was " + s.size());
        check(s.contains(Color.RED), "RED should be present");
        check(s.contains(Color.BLUE), "BLUE should be present");
        check(!s.contains(Color.GREEN), "GREEN should be absent");
    }

    static void testSameElementTwice() {
        EnumSet<Color> s = EnumSet.of(Color.GREEN, Color.GREEN);
        check(s.size() == 1, "expected size 1 for duplicate input but was " + s.size());
        check(s.contains(Color.GREEN), "GREEN should be present");
    }

    static void testNaturalOrder() {
        EnumSet<Color> s = EnumSet.of(Color.YELLOW, Color.RED);
        Iterator<Color> it = s.iterator();
        check(it.hasNext() && it.next() == Color.RED, "first element should be RED");
        check(it.hasNext() && it.next() == Color.YELLOW, "second element should be YELLOW");
        check(!it.hasNext(), "no more elements expected");
    }

    static void testFreshInstance() {
        EnumSet<Color> a = EnumSet.of(Color.RED, Color.GREEN);
        EnumSet<Color> b = EnumSet.of(Color.RED, Color.GREEN);
        check(a != b, "each call should return a new set");
        a.remove(Color.RED);
        check(b.contains(Color.RED), "modifying one set must not affect another");
        check(b.size() == 2, "second set size should remain 2");
    }

    static void testNullFirstElement() {
        boolean thrown = false;
        try {
            EnumSet.of(null, Color.RED);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null first element should throw NullPointerException");
    }

    static void testNullSecondElement() {
        boolean thrown = false;
        try {
            EnumSet.of(Color.RED, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null second element should throw NullPointerException");
    }
}
