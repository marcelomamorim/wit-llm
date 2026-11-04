/*
 * @test
 * @summary Verifies EnumSet iterator remove() behavior for RegularEnumSet via public API
 * @run main RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class RegularEnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testRemoveFirstElement();
        testRemoveWithoutNextThrows();
        testDoubleRemoveThrows();
        testRemoveMiddleElement();
        testRemoveAllElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveFirstElement() {
        EnumSet<Color> set = EnumSet.allOf(Color.class);
        Iterator<Color> it = set.iterator();
        check(it.next() == Color.RED, "first element should be RED");
        it.remove();
        check(!set.contains(Color.RED), "RED should be removed");
        check(set.contains(Color.GREEN), "GREEN should remain");
        check(set.contains(Color.BLUE), "BLUE should remain");
        check(set.size() == 2, "size should be 2 but was " + set.size());
    }

    static void testRemoveWithoutNextThrows() {
        EnumSet<Color> set = EnumSet.of(Color.RED);
        Iterator<Color> it = set.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(set.contains(Color.RED), "set should be unchanged");
        check(set.size() == 1, "size should remain 1");
    }

    static void testDoubleRemoveThrows() {
        EnumSet<Color> set = EnumSet.allOf(Color.class);
        Iterator<Color> it = set.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(set.size() == 2, "only one removal should have occurred");
        check(!set.contains(Color.RED), "RED should be removed");
    }

    static void testRemoveMiddleElement() {
        EnumSet<Color> set = EnumSet.allOf(Color.class);
        Iterator<Color> it = set.iterator();
        check(it.next() == Color.RED, "first should be RED");
        check(it.next() == Color.GREEN, "second should be GREEN");
        it.remove();
        check(set.contains(Color.RED), "RED should remain");
        check(!set.contains(Color.GREEN), "GREEN should be removed");
        check(set.contains(Color.BLUE), "BLUE should remain");
        check(it.hasNext(), "iterator should still have next");
        check(it.next() == Color.BLUE, "next should be BLUE");
    }

    static void testRemoveAllElements() {
        EnumSet<Color> set = EnumSet.allOf(Color.class);
        Iterator<Color> it = set.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(set.isEmpty(), "set should be empty after removing all elements");
        check(!it.hasNext(), "iterator should be exhausted");
    }
}
