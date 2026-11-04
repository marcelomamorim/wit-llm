/*
 * @test
 * @summary Verify Iterator.remove() state checks of the EnumSet iterator backed by RegularEnumSet
 * @run main RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class RegularEnumSetWitupTest {
    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveThrows();
        testRemoveAfterExhaustion();
    }

    static void testRemoveBeforeNextThrows() {
        Iterator<Color> it = EnumSet.allOf(Color.class).iterator();
        try {
            it.remove();
            fail("expected IllegalStateException when remove() is called before next()");
        } catch (IllegalStateException expected) {
        }
    }

    static void testRemoveAfterNextRemovesElement() {
        Set<Color> set = EnumSet.of(Color.RED, Color.BLUE);
        Iterator<Color> it = set.iterator();
        Color first = it.next();
        if (first != Color.RED) {
            fail("expected RED as first element but was " + first);
        }
        it.remove();
        if (set.contains(Color.RED)) {
            fail("RED should have been removed");
        }
        if (!set.contains(Color.BLUE)) {
            fail("BLUE should still be present");
        }
        if (set.size() != 1) {
            fail("expected size 1 but was " + set.size());
        }
    }

    static void testSecondRemoveThrows() {
        Iterator<Color> it = EnumSet.allOf(Color.class).iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            fail("expected IllegalStateException on second remove() without next()");
        } catch (IllegalStateException expected) {
        }
    }

    static void testRemoveAfterExhaustion() {
        Set<Color> set = EnumSet.of(Color.GREEN);
        Iterator<Color> it = set.iterator();
        it.next();
        it.remove();
        if (it.hasNext()) {
            fail("iterator should be exhausted");
        }
        if (!set.isEmpty()) {
            fail("set should be empty but was " + set);
        }
        try {
            it.remove();
            fail("expected IllegalStateException after removing the last returned element");
        } catch (IllegalStateException expected) {
        }
    }

    static void fail(String message) {
        throw new AssertionError(message);
    }
}
