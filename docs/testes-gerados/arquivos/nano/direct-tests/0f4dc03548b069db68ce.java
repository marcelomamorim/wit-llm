/*
 * @test
 * @summary Test EnumSet.noneOf() method for various enum types.
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }
    enum Size { SMALL, MEDIUM, LARGE }

    public static void main(String[] args) {
        testNoneOfColor();
        testNoneOfSize();
        testInvalidClass();
    }

    static void testNoneOfColor() {
        EnumSet<Color> set = EnumSet.noneOf(Color.class);
        assert set.isEmpty() : "EnumSet should be empty";
        assert set.size() == 0 : "EnumSet size should be 0";
        assert set.contains(Color.RED) == false : "Should not contain RED";
        set.add(Color.RED);
        assert set.contains(Color.RED) : "Should contain RED after add";
    }

    static void testNoneOfSize() {
        EnumSet<Size> set = EnumSet.noneOf(Size.class);
        assert set.isEmpty() : "EnumSet should be empty";
        set.add(Size.LARGE);
        assert set.contains(Size.LARGE) : "Should contain LARGE";
        set.remove(Size.LARGE);
        assert !set.contains(Size.LARGE) : "Should not contain LARGE after removal";
    }

    static void testInvalidClass() {
        try {
            EnumSet.noneOf(String.class);
            assert false : "Expected ClassCastException";
        } catch (ClassCastException e) {
            assert e.getMessage().contains("not an enum") : "Exception message should mention not an enum";
        }
    }
}
