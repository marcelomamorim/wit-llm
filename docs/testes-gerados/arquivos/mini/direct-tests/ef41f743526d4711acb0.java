/*
 * @test
 * @summary Test java.util.JumboEnumSet remove() method behavior
 * @run main JumboEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class JumboEnumSetWitupTest {

    enum LargeEnum {
        E0, E1, E2, E3, E4, E5, E6, E7, E8, E9,
        E10, E11, E12, E13, E14, E15, E16, E17, E18, E19,
        E20, E21, E22, E23, E24, E25, E26, E27, E28, E29,
        E30, E31, E32, E33, E34, E35, E36, E37, E38, E39,
        E40, E41, E42, E43, E44, E45, E46, E47, E48, E49,
        E50, E51, E52, E53, E54, E55, E56, E57, E58, E59,
        E60, E61, E62, E63, E64, E65, E66, E67
    }

    public static void main(String[] args) {
        testRemoveWithoutNext();
        testRemoveAfterNext();
    }

    static void testRemoveWithoutNext() {
        EnumSet<LargeEnum> set = EnumSet.noneOf(LargeEnum.class);
        set.add(LargeEnum.E0);
        Iterator<LargeEnum> it = set.iterator();
        try {
            // Directly call remove() without next() should throw IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testRemoveAfterNext() {
        EnumSet<LargeEnum> set = EnumSet.noneOf(LargeEnum.class);
        set.add(LargeEnum.E0);
        set.add(LargeEnum.E1);
        Iterator<LargeEnum> it = set.iterator();

        // Call next() to get first element
        LargeEnum first = it.next();
        if (first != LargeEnum.E0 && first != LargeEnum.E1) {
            throw new RuntimeException("Unexpected first element: " + first);
        }

        // Remove the element just returned
        it.remove();

        // The set size should be decreased by one
        if (set.size() != 1) {
            throw new RuntimeException("Set size after remove should be 1 but was " + set.size());
        }

        // Calling remove() again without next() should throw IllegalStateException
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
