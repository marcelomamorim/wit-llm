/*
 * @test
 * @summary Verifies Iterator.remove() on EnumSet iterators backed by JumboEnumSet (enums with more than 64 constants)
 * @run main JumboEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class JumboEnumSetWitupTest {

    enum Big {
        E0, E1, E2, E3, E4, E5, E6, E7, E8, E9,
        E10, E11, E12, E13, E14, E15, E16, E17, E18, E19,
        E20, E21, E22, E23, E24, E25, E26, E27, E28, E29,
        E30, E31, E32, E33, E34, E35, E36, E37, E38, E39,
        E40, E41, E42, E43, E44, E45, E46, E47, E48, E49,
        E50, E51, E52, E53, E54, E55, E56, E57, E58, E59,
        E60, E61, E62, E63, E64, E65, E66, E67, E68, E69
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveFirstElement() {
        EnumSet<Big> set = EnumSet.allOf(Big.class);
        check(set.size() == 70, "initial size should be 70");
        Iterator<Big> it = set.iterator();
        Big first = it.next();
        check(first == Big.E0, "first element should be E0");
        it.remove();
        check(!set.contains(Big.E0), "E0 should be removed");
        check(set.size() == 69, "size should decrease to 69");
        check(set.contains(Big.E1), "E1 should remain");
    }

    static void testRemoveAcrossWordBoundary() {
        EnumSet<Big> set = EnumSet.allOf(Big.class);
        Iterator<Big> it = set.iterator();
        Big target = null;
        while (it.hasNext()) {
            Big b = it.next();
            if (b == Big.E64) {
                target = b;
                break;
            }
        }
        check(target == Big.E64, "should reach E64");
        it.remove();
        check(!set.contains(Big.E64), "E64 should be removed from second word");
        check(set.contains(Big.E63), "E63 should remain");
        check(set.contains(Big.E65), "E65 should remain");
        check(set.size() == 69, "size should be 69");
    }

    static void testRemoveBeforeNextThrows() {
        EnumSet<Big> set = EnumSet.allOf(Big.class);
        Iterator<Big> it = set.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() should throw IllegalStateException");
        check(set.size() == 70, "set should be unchanged");
    }

    static void testDoubleRemoveThrows() {
        EnumSet<Big> set = EnumSet.allOf(Big.class);
        Iterator<Big> it = set.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() should throw IllegalStateException");
        check(set.size() == 69, "size should remain 69 after failed second remove");
    }

    public static void main(String[] args) {
        testRemoveFirstElement();
        testRemoveAcrossWordBoundary();
        testRemoveBeforeNextThrows();
        testDoubleRemoveThrows();
    }
}
