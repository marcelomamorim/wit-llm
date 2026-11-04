/*
 * @test
 * @summary Verifies that replaceAll on a Collections unmodifiable list throws UnsupportedOperationException and leaves the list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUOE();
        testReplaceAllWithNullOperatorStillThrowsUOE();
        testListUnchangedAfterReplaceAllFailure();
        testEmptyUnmodifiableListReplaceAllThrowsUOE();
    }

    static void testReplaceAllThrowsUOE() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> list = Collections.unmodifiableList(base);
        boolean thrown = false;
        try {
            list.replaceAll(s -> s.toUpperCase());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
    }

    static void testReplaceAllWithNullOperatorStillThrowsUOE() {
        List<String> base = new ArrayList<>(Arrays.asList("x"));
        List<String> list = Collections.unmodifiableList(base);
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for null operator");
        }
    }

    static void testListUnchangedAfterReplaceAllFailure() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> list = Collections.unmodifiableList(base);
        try {
            list.replaceAll(s -> s + "!");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (!list.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("List was modified: " + list);
        }
    }

    static void testEmptyUnmodifiableListReplaceAllThrowsUOE() {
        List<String> list = Collections.unmodifiableList(new ArrayList<String>());
        boolean thrown = false;
        try {
            list.replaceAll(s -> s);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException on empty unmodifiable list");
        }
    }
}
