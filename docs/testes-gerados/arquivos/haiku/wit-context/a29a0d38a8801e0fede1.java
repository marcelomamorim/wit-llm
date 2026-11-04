/*
 * @test
 * @summary Verifies that ListIterator.add on an unmodifiable list view throws UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsOnUnmodifiableListIterator();
        testAddWithNullElementThrows();
        testAddFromNonZeroIndexThrows();
        testBackingListUnchangedAfterFailedAdd();
    }

    private static void testAddThrowsOnUnmodifiableListIterator() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        List<String> view = Collections.unmodifiableList(backing);
        ListIterator<String> it = view.listIterator();
        expectUnsupported(() -> it.add("x"));
    }

    private static void testAddWithNullElementThrows() {
        List<String> view = Collections.unmodifiableList(new ArrayList<String>());
        ListIterator<String> it = view.listIterator();
        expectUnsupported(() -> it.add(null));
    }

    private static void testAddFromNonZeroIndexThrows() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        List<String> view = Collections.unmodifiableList(backing);
        ListIterator<String> it = view.listIterator(1);
        expectUnsupported(() -> it.add("x"));
    }

    private static void testBackingListUnchangedAfterFailedAdd() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        List<String> view = Collections.unmodifiableList(backing);
        ListIterator<String> it = view.listIterator();
        expectUnsupported(() -> it.add("x"));
        if (view.size() != 1 || !"a".equals(view.get(0))) {
            throw new AssertionError("view changed after failed add");
        }
        if (backing.size() != 1 || !"a".equals(backing.get(0))) {
            throw new AssertionError("backing list changed after failed add");
        }
    }

    private static void expectUnsupported(Runnable action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
