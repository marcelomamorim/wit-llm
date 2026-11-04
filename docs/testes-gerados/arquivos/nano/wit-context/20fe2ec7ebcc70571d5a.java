/*
 * @test
 * @summary Test for ArrayList.removeIf to verify behavior with null predicate.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    public static void testRemoveIfWithNullPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.removeIf(null);
            // If no exception, test passes as behavior is to throw NullPointerException
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testRemoveIfWithValidPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        boolean result = list.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer t) {
                return t % 2 == 0;
            }
        });
        // After removal, list should contain only odd numbers
        assert list.size() == 2 : "Expected size 2, got " + list.size();
        assert list.contains(1) : "List should contain 1";
        assert list.contains(3) : "List should contain 3";
        assert result : "removeIf should return true when elements are removed";
    }
}
