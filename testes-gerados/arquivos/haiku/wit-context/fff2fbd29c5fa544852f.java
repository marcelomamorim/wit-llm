/*
 * @test
 * @summary Witup-derived checks for RegularEnumSet iterator next() exercised through the public EnumSet API
 * @run main RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        checkEmptySetNextThrows();
        checkSingleElementNextThenThrows();
        checkFullSetOrderAndExhaustion();
    }

    static void checkEmptySetNextThrows() {
        Iterator<Color> it = EnumSet.noneOf(Color.class).iterator();
        if (it.hasNext()) {
            throw new RuntimeException("empty set iterator reports hasNext");
        }
        try {
            it.next();
            throw new RuntimeException("next() on empty set did not throw");
        } catch (NoSuchElementException expected) {
        }
    }

    static void checkSingleElementNextThenThrows() {
        Iterator<Color> it = EnumSet.of(Color.GREEN).iterator();
        if (it.next() != Color.GREEN) {
            throw new RuntimeException("unexpected element for singleton set");
        }
        if (it.hasNext()) {
            throw new RuntimeException("singleton iterator has extra element");
        }
        try {
            it.next();
            throw new RuntimeException("next() after exhaustion did not throw");
        } catch (NoSuchElementException expected) {
        }
    }

    static void checkFullSetOrderAndExhaustion() {
        Iterator<Color> it = EnumSet.allOf(Color.class).iterator();
        if (it.next() != Color.RED) {
            throw new RuntimeException("first element out of natural order");
        }
        if (it.next() != Color.GREEN) {
            throw new RuntimeException("second element out of natural order");
        }
        if (it.next() != Color.BLUE) {
            throw new RuntimeException("third element out of natural order");
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator not exhausted after all elements");
        }
        try {
            it.next();
            throw new RuntimeException("next() after full traversal did not throw");
        } catch (NoSuchElementException expected) {
        }
    }
}
