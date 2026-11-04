/*
 * @test
 * @summary Verify that the unmodifiable List view returned by Collections.unmodifiableList rejects replaceAll and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUnsupportedOperation();
        testReplaceAllLeavesBackingListUnchanged();
        testReplaceAllWithNullOperatorThrowsUnsupportedOperation();
    }

    static void testReplaceAllThrowsUnsupportedOperation() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> view = Collections.unmodifiableList(base);
        boolean thrown = false;
        try {
            view.replaceAll(s -> s + "x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from replaceAll");
        }
    }

    static void testReplaceAllLeavesBackingListUnchanged() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> view = Collections.unmodifiableList(base);
        try {
            view.replaceAll(s -> s.toUpperCase());
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (!base.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("Backing list was modified: " + base);
        }
    }

    static void testReplaceAllWithNullOperatorThrowsUnsupportedOperation() {
        List<String> base = new ArrayList<>(Arrays.asList("a"));
        List<String> view = Collections.unmodifiableList(base);
        boolean thrown = false;
        try {
            view.replaceAll(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for null operator");
        }
    }
}
