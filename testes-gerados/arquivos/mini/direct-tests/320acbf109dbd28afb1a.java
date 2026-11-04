/*
 * @test
 * @summary Test javax.security.auth.Subject addAll method behavior
 * @run main SubjectWitupTest
 */

import javax.security.auth.Subject;
import java.util.*;

public class SubjectWitupTest {
    public static void main(String[] args) {
        testAddAllWithEmptyCollection();
        testAddAllWithNonEmptyCollection();
        testAddAllWithCollectionContainingDuplicates();
        testAddAllWithCollectionContainingNull();
    }

    private static void testAddAllWithEmptyCollection() {
        Subject subject = new Subject();
        Collection<Object> empty = Collections.emptyList();
        boolean changed = subject.addAll(empty);
        if (changed) {
            throw new RuntimeException("addAll should return false when adding empty collection");
        }
    }

    private static void testAddAllWithNonEmptyCollection() {
        Subject subject = new Subject();
        Collection<Object> items = new ArrayList<>();
        items.add("item1");
        items.add("item2");
        boolean changed = subject.addAll(items);
        if (!changed) {
            throw new RuntimeException("addAll should return true when adding non-empty collection");
        }
        if (!subject.contains("item1") || !subject.contains("item2")) {
            throw new RuntimeException("Subject should contain all added items");
        }
    }

    private static void testAddAllWithCollectionContainingDuplicates() {
        Subject subject = new Subject();
        subject.add("item1");
        Collection<Object> items = new ArrayList<>();
        items.add("item1"); // duplicate
        items.add("item2");
        boolean changed = subject.addAll(items);
        if (!changed) {
            throw new RuntimeException("addAll should return true when at least one new item is added");
        }
        if (!subject.contains("item1") || !subject.contains("item2")) {
            throw new RuntimeException("Subject should contain all items after addAll");
        }
    }

    private static void testAddAllWithCollectionContainingNull() {
        Subject subject = new Subject();
        Collection<Object> items = new ArrayList<>();
        items.add(null);
        try {
            subject.addAll(items);
            throw new RuntimeException("addAll should throw NullPointerException when collection contains null");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
