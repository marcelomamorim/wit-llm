/*
 * @test
 * @summary Verify EnumSet.allOf returns a set containing all constants of the enum type
 * @run main EnumSetWitupTest
 */
import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {
    enum Color { RED, GREEN, BLUE }
    enum Empty { }

    public static void main(String[] args) {
        testAllConstants();
        testEmptyEnum();
        testResultIsFreshSet();
    }

    static void testAllConstants() {
        EnumSet<Color> s = EnumSet.allOf(Color.class);
        check(s.size() == 3, "size should be 3");
        check(s.contains(Color.RED), "RED missing");
        check(s.contains(Color.GREEN), "GREEN missing");
        check(s.contains(Color.BLUE), "BLUE missing");
        Iterator<Color> it = s.iterator();
        check(it.next() == Color.RED, "order: first should be RED");
        check(it.next() == Color.GREEN, "order: second should be GREEN");
        check(it.next() == Color.BLUE, "order: third should be BLUE");
        check(!it.hasNext(), "unexpected extra element");
    }

    static void testEmptyEnum() {
        EnumSet<Empty> s = EnumSet.allOf(Empty.class);
        check(s.isEmpty(), "allOf on enum without constants should be empty");
        check(s.size() == 0, "size should be 0");
    }

    static void testResultIsFreshSet() {
        EnumSet<Color> first = EnumSet.allOf(Color.class);
        first.remove(Color.RED);
        check(!first.contains(Color.RED), "remove should take effect on returned set");
        EnumSet<Color> second = EnumSet.allOf(Color.class);
        check(second.size() == 3, "new allOf call should return all constants");
        check(second.contains(Color.RED), "new allOf call should contain RED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
