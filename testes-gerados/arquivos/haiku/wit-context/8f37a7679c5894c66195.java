/*
 * @test
 * @summary EnumSet.range returns inclusive ranges and rejects reversed bounds
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Set;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW }

    public static void main(String[] args) {
        Set<Color> s = EnumSet.range(Color.GREEN, Color.BLUE);
        check(s.size() == 2, "GREEN..BLUE size");
        check(s.contains(Color.GREEN) && s.contains(Color.BLUE), "GREEN..BLUE bounds included");
        check(!s.contains(Color.RED) && !s.contains(Color.YELLOW), "GREEN..BLUE excludes outside");

        Set<Color> single = EnumSet.range(Color.RED, Color.RED);
        check(single.size() == 1 && single.contains(Color.RED), "RED..RED single element");

        Set<Color> all = EnumSet.range(Color.RED, Color.YELLOW);
        check(all.size() == Color.values().length, "RED..YELLOW full range");
        for (Color c : Color.values()) {
            check(all.contains(c), "full range contains " + c);
        }

        Set<Color> a = EnumSet.range(Color.RED, Color.GREEN);
        Set<Color> b = EnumSet.range(Color.RED, Color.GREEN);
        a.remove(Color.RED);
        check(b.contains(Color.RED), "range returns independent sets");

        boolean thrown = false;
        try {
            EnumSet.range(Color.BLUE, Color.RED);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "reversed bounds throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
