/* @test    @summary Test EnumSet.allOf() with various enum types    @run main EnumSetWitupTest */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }
    enum Size { SMALL, MEDIUM, LARGE }

    public static void main(String[] args) {
        testAllOfColor();
        testAllOfSize();
        testAllOfEmpty();
    }

    static void testAllOfColor() {
        EnumSet<Color> set = EnumSet.allOf(Color.class);
        assert set.contains(Color.RED) : "Should contain RED";
        assert set.contains(Color.GREEN) : "Should contain GREEN";
        assert set.contains(Color.BLUE) : "Should contain BLUE";
        assert set.size() == 3 : "Size should be 3";
    }

    static void testAllOfSize() {
        EnumSet<Size> set = EnumSet.allOf(Size.class);
        assert set.contains(Size.SMALL) : "Should contain SMALL";
        assert set.contains(Size.MEDIUM) : "Should contain MEDIUM";
        assert set.contains(Size.LARGE) : "Should contain LARGE";
        assert set.size() == 3 : "Size should be 3";
    }

    static void testAllOfEmpty() {
        // Testing with a dummy enum with no constants
        enum EmptyEnum {}
        EnumSet<EmptyEnum> emptySet = EnumSet.allOf(EmptyEnum.class);
        assert emptySet.isEmpty() : "Empty enum set should be empty";
    }
}
