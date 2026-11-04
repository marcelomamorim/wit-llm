/*
 * @test
 * @summary Checks forEach on Collections.unmodifiableCollection: null action is rejected, elements are visited in order, and the wrapper does not change its contents.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testForEachVisitsElementsInOrder();
        testForEachOnEmptyWrapperDoesNotInvokeAction();
        testForEachDoesNotModifyWrappedContents();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> wrapper = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            wrapper.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testForEachVisitsElementsInOrder() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        backing.add("c");
        Collection<String> wrapper = Collections.unmodifiableCollection(backing);
        List<String> visited = new ArrayList<>();
        Consumer<String> action = s -> visited.add(s);
        wrapper.forEach(action);
        check(visited.size() == 3, "expected 3 visited elements but got " + visited.size());
        check(visited.get(0).equals("a"), "first element must be a");
        check(visited.get(1).equals("b"), "second element must be b");
        check(visited.get(2).equals("c"), "third element must be c");
    }

    static void testForEachOnEmptyWrapperDoesNotInvokeAction() {
        Collection<String> wrapper = Collections.unmodifiableCollection(new ArrayList<String>());
        int[] calls = new int[1];
        Consumer<String> action = s -> calls[0]++;
        wrapper.forEach(action);
        check(calls[0] == 0, "action must not be invoked for an empty collection");
    }

    static void testForEachDoesNotModifyWrappedContents() {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        backing.add("y");
        Collection<String> wrapper = Collections.unmodifiableCollection(backing);
        List<String> visited = new ArrayList<>();
        wrapper.forEach(s -> visited.add(s));
        check(wrapper.size() == 2, "wrapper size must remain 2");
        check(backing.get(0).equals("x") && backing.get(1).equals("y"),
              "wrapped contents must remain unchanged");
    }
}
