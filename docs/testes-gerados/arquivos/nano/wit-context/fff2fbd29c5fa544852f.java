/* @test    @summary Test for RegularEnumSet.next() method to verify behavior when set is empty and non-empty.    @run main RegularEnumSetWitupTest */

import java.util.RegularEnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum SampleEnum {A, B, C}

    public static void main(String[] args) {
        testNextOnEmptySet();
        testNextOnNonEmptySet();
    }

    static void testNextOnEmptySet() {
        RegularEnumSet<SampleEnum> set = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        set.addAll(); // fill set with all elements
        set.complement(); // invert to empty set
        Iterator<SampleEnum> iterator = set.iterator();
        try {
            while (true) {
                iterator.next();
            }
        } catch (NoSuchElementException e) {
            // Expected exception when calling next() on empty iterator
        }
        // Verify that calling next() when no elements are left throws exception
        iterator = set.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        try {
            iterator.next();
            // Should not reach here
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }

    static void testNextOnNonEmptySet() {
        RegularEnumSet<SampleEnum> set = new RegularEnumSet<>(SampleEnum.class, SampleEnum.values());
        set.addRange(SampleEnum.A, SampleEnum.C); // add A, B, C
        Iterator<SampleEnum> iterator = set.iterator();
        boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            throw new AssertionError("Iterator should have elements");
        }
        SampleEnum first = iterator.next();
        if (first == null) {
            throw new AssertionError("next() returned null");
        }
        // Consume remaining elements
        while (iterator.hasNext()) {
            iterator.next();
        }
        // After exhausting, next() should throw
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }
}
