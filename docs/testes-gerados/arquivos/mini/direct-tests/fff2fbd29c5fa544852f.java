/*
 * @test
 * @summary Test RegularEnumSet.EnumSetIterator.next() method behavior
 * @run main RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum TestEnum { A, B, C }

    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsWhenNoMoreElements();
    }

    static void testNextReturnsElementsInOrder() {
        // Create a RegularEnumSet with elements A and C
        RegularEnumSet<TestEnum> set = new RegularEnumSet<>(TestEnum.class, TestEnum.class.getEnumConstants());
        set.addRange(TestEnum.A, TestEnum.A); // add A
        set.addRange(TestEnum.C, TestEnum.C); // add C

        Iterator<TestEnum> it = set.iterator();

        // The iterator should return A then C
        TestEnum first = it.next();
        if (first != TestEnum.A) {
            throw new AssertionError("Expected first element A, got " + first);
        }

        TestEnum second = it.next();
        if (second != TestEnum.C) {
            throw new AssertionError("Expected second element C, got " + second);
        }

        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements");
        }
    }

    static void testNextThrowsWhenNoMoreElements() {
        RegularEnumSet<TestEnum> set = new RegularEnumSet<>(TestEnum.class, TestEnum.class.getEnumConstants());
        // empty set

        Iterator<TestEnum> it = set.iterator();

        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no elements");
        }

        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
