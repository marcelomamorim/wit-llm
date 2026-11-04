/*
 * @test
 * @summary Verify Collections wrapper removeIf delegation, null filter rejection and unmodifiable behavior
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testSynchronizedRemoveIfRemovesMatching();
        testCheckedRemoveIfRemovesMatching();
        testUnmodifiableRemoveIfThrowsUOE();
    }

    static void testNullFilterThrowsNPE() {
        ArrayList<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> sync = Collections.synchronizedCollection(base);
        boolean thrown = false;
        try {
            sync.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null filter");
        }
        if (!base.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("collection modified after null filter: " + base);
        }
    }

    static void testSynchronizedRemoveIfRemovesMatching() {
        ArrayList<String> base = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        Collection<String> sync = Collections.synchronizedCollection(base);
        boolean changed = sync.removeIf(s -> s.length() > 1);
        if (!changed) {
            throw new AssertionError("removeIf should report change");
        }
        if (!base.equals(Arrays.asList("a"))) {
            throw new AssertionError("unexpected contents: " + base);
        }
        if (sync.removeIf(s -> s.length() > 10)) {
            throw new AssertionError("removeIf should report no change when nothing matches");
        }
    }

    static void testCheckedRemoveIfRemovesMatching() {
        ArrayList<String> base = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        Collection<String> chk = Collections.checkedCollection(base, String.class);
        boolean changed = chk.removeIf(s -> s.length() > 1);
        if (!changed) {
            throw new AssertionError("checked removeIf should report change");
        }
        if (!base.equals(Arrays.asList("a"))) {
            throw new AssertionError("unexpected contents: " + base);
        }
    }

    static void testUnmodifiableRemoveIfThrowsUOE() {
        ArrayList<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> um = Collections.unmodifiableCollection(base);
        boolean thrown = false;
        try {
            um.removeIf(s -> true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException");
        }
        if (!base.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("unmodifiable view altered backing list: " + base);
        }
    }
}
