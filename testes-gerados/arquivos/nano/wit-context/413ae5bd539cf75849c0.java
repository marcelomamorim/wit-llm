/*
 * @test
 * @summary Tests for CopyOnWriteArrayList.removeIf method
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    static void testRemoveIfWithNullPredicate() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        boolean exceptionThrown = false;
        try {
            list.removeIf(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        }
    }

    static void testRemoveIfWithValidPredicate() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        boolean result = list.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer t) {
                return t % 2 == 0;
            }
        });
        if (!result) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (list.size() != 2 || list.contains(2)) {
            throw new AssertionError("List should contain only odd numbers after removal");
        }
    }
}
