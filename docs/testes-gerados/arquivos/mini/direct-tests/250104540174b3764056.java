/*
 * @test
 * @summary Test RegularEnumSet.EnumSetIterator remove() method behavior
 * @run main RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum TestEnum { A, B, C }

    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
        testRemoveTwiceThrows();
    }

    static void testRemoveWithoutNextThrows() {
        EnumSet<TestEnum> set = EnumSet.noneOf(TestEnum.class);
        set.add(TestEnum.A);
        Iterator<TestEnum> it = set.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testRemoveAfterNextRemovesElement() {
        EnumSet<TestEnum> set = EnumSet.allOf(TestEnum.class);
        Iterator<TestEnum> it = set.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        TestEnum first = it.next();
        it.remove();
        if (set.contains(first)) {
            throw new RuntimeException("Element was not removed by iterator.remove()");
        }
    }

    static void testRemoveTwiceThrows() {
        EnumSet<TestEnum> set = EnumSet.allOf(TestEnum.class);
        Iterator<TestEnum> it = set.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
