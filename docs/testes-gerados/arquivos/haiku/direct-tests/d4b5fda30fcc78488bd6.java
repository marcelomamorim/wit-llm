/*
 * @test
 * @summary Verifies that set(int, E) on a list returned by Collections.unmodifiableList throws UnsupportedOperationException and leaves the list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSetOnNonEmptyUnmodifiableListThrows();
        testSetOnEmptyUnmodifiableListThrows();
        testSetWithNullElementThrows();
    }

    static void testSetOnNonEmptyUnmodifiableListThrows() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        List<String> unmod = Collections.unmodifiableList(backing);
        expectUnsupported(() -> unmod.set(0, "x"), "set on non-empty unmodifiable list");
        if (!"a".equals(unmod.get(0)) || !"b".equals(unmod.get(1)) || unmod.size() != 2) {
            throw new AssertionError("unmodifiable list was changed by set");
        }
        if (!"a".equals(backing.get(0))) {
            throw new AssertionError("backing list was changed by set");
        }
    }

    static void testSetOnEmptyUnmodifiableListThrows() {
        List<String> unmod = Collections.unmodifiableList(new ArrayList<String>());
        expectUnsupported(() -> unmod.set(0, "x"), "set on empty unmodifiable list");
        if (!unmod.isEmpty()) {
            throw new AssertionError("empty unmodifiable list became non-empty");
        }
    }

    static void testSetWithNullElementThrows() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        List<String> unmod = Collections.unmodifiableList(backing);
        expectUnsupported(() -> unmod.set(0, null), "set with null element on unmodifiable list");
        if (!"a".equals(unmod.get(0))) {
            throw new AssertionError("element changed after failed set with null");
        }
    }

    interface Action {
        void run();
    }

    static void expectUnsupported(Action action, String description) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException: " + description);
    }
}
