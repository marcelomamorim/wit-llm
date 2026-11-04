/*
 * @test
 * @summary Test RegularEnumSet.EnumSetIterator.remove() method behavior including exception throwing
 * @run main java.util.RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum TestEnum { A, B, C }

    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
        testIteratorHasNextAndNextBehavior();
    }

    private static void testRemoveWithoutNextThrows() {
        EnumSet<TestEnum> set = EnumSet.noneOf(TestEnum.class);
        Iterator<TestEnum> it = set.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAfterNextRemovesElement() {
        EnumSet<TestEnum> set = EnumSet.noneOf(TestEnum.class);
        set.add(TestEnum.A);
        Iterator<TestEnum> it = set.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        TestEnum e = it.next();
        if (e != TestEnum.A) {
            throw new RuntimeException("Iterator returned unexpected element");
        }
        it.remove();
        if (set.contains(TestEnum.A)) {
            throw new RuntimeException("Element was not removed by iterator.remove()");
        }
        // Calling remove again without next should throw
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown on second remove");
        } catch (IllegalStateException expected) {
            // Expected
        }
    }

    private static void testIteratorHasNextAndNextBehavior() {
        EnumSet<TestEnum> set = EnumSet.noneOf(TestEnum.class);
        set.add(TestEnum.B);
        Iterator<TestEnum> it = set.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        TestEnum e = it.next();
        if (e != TestEnum.B) {
            throw new RuntimeException("Iterator returned unexpected element");
        }
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no more elements");
        }
        try {
            it.next();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected
        }
    }
}
