/*
 * @test
 * @summary Witup-generated behavioral tests for EnumSet.of(E, E, E)
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Color e1, Color e2, Color e3, String label) {
        try {
            EnumSet.of(e1, e2, e3);
            throw new RuntimeException("NullPointerException expected: " + label);
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        Set<Color> s = EnumSet.of(Color.BLUE, Color.RED, Color.GREEN);
        check(s.size() == 3, "size should be 3");
        check(s.contains(Color.RED), "RED missing");
        check(s.contains(Color.GREEN), "GREEN missing");
        check(s.contains(Color.BLUE), "BLUE missing");
        check(!s.contains(Color.YELLOW), "YELLOW should not be present");

        Iterator<Color> it = s.iterator();
        check(it.next() == Color.RED, "natural order: first should be RED");
        check(it.next() == Color.GREEN, "natural order: second should be GREEN");
        check(it.next() == Color.BLUE, "natural order: third should be BLUE");
        check(!it.hasNext(), "iterator should be exhausted");

        Set<Color> dup = EnumSet.of(Color.RED, Color.RED, Color.RED);
        check(dup.size() == 1, "duplicates should collapse to one element");
        check(dup.contains(Color.RED), "RED missing in duplicate set");

        check(s.add(Color.YELLOW), "add of absent element should return true");
        check(s.size() == 4, "size should be 4 after add");

        expectNpe(null, Color.GREEN, Color.BLUE, "null e1");
        expectNpe(Color.RED, null, Color.BLUE, "null e2");
        expectNpe(Color.RED, Color.GREEN, null, "null e3");
    }
}
