/* @test    @summary Test EnumSet.of(E e1, E e2) method for correctness    @run main EnumSetWitupTest */

import java.util.EnumSet;
import java.util.Set;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testEnumSetOfE2();
    }

    static void testEnumSetOfE2() {
        EnumSet<Color> set = EnumSet.of(Color.RED, Color.GREEN);
        assert set.contains(Color.RED) : "Set should contain RED";
        assert set.contains(Color.GREEN) : "Set should contain GREEN";
        assert !set.contains(Color.BLUE) : "Set should not contain BLUE";
        assert set.size() == 2 : "Set size should be 2";
    }
}
