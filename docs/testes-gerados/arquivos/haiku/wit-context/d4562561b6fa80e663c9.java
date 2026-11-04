/*
 * @test
 * @summary Behavioral checks for EnumSet.allOf(Class), including the non-enum class rejection path
 * @run main EnumSetWitupTest
 */

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    enum Empty { }

    public static void main(String[] args) {
        EnumSet<Color> colors = EnumSet.allOf(Color.class);
        check(colors.size() == 3, "allOf should contain all constants");
        check(colors.contains(Color.RED), "missing RED");
        check(colors.contains(Color.GREEN), "missing GREEN");
        check(colors.contains(Color.BLUE), "missing BLUE");

        List<Color> order = new ArrayList<>(colors);
        List<Color> expected = new ArrayList<>();
        expected.add(Color.RED);
        expected.add(Color.GREEN);
        expected.add(Color.BLUE);
        check(order.equals(expected), "iteration must follow declaration order");

        EnumSet<Empty> empty = EnumSet.allOf(Empty.class);
        check(empty.isEmpty(), "allOf on enum without constants must be empty");

        colors.remove(Color.RED);
        EnumSet<Color> fresh = EnumSet.allOf(Color.class);
        check(fresh.size() == 3, "each call must return a new independent set");

        checkNonEnumRejected();
    }

    @SuppressWarnings("unchecked")
    static void checkNonEnumRejected() {
        boolean thrown = false;
        try {
            EnumSet.allOf((Class) Object.class);
        } catch (ClassCastException expected) {
            thrown = true;
        }
        check(thrown, "allOf with a non-enum class must throw ClassCastException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
