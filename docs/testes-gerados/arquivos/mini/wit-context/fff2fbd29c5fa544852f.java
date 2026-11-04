/*
 * @test
 * @summary Test java.util.RegularEnumSet.EnumSetIterator.next() method behavior including NoSuchElementException
 * @run main java.util.RegularEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum TestEnum { A, B, C }

    public static void main(String[] args) {
        testNextWithElements();
        testNextThrowsNoSuchElementException();
    }

    static void testNextWithElements() {
        EnumSet<TestEnum> set = EnumSet.of(TestEnum.A, TestEnum.C);
        Iterator<TestEnum> it = set.iterator();

        // The iterator should return A then C in natural order
        TestEnum first = it.next();
        if (first != TestEnum.A) {
            throw new AssertionError("Expected first element A, got " + first);
        }

        TestEnum second = it.next();
        if (second != TestEnum.C) {
            throw new AssertionError("Expected second element C, got " + second);
        }

        // After all elements are returned, hasNext() should be false
        if (it.hasNext()) {
            throw new AssertionError("Expected hasNext() to be false after all elements returned");
        }
    }

    static void testNextThrowsNoSuchElementException() {
        EnumSet<TestEnum> emptySet = EnumSet.noneOf(TestEnum.class);
        Iterator<TestEnum> it = emptySet.iterator();

        if (it.hasNext()) {
            throw new AssertionError("Expected hasNext() to be false for empty set");
        }

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
