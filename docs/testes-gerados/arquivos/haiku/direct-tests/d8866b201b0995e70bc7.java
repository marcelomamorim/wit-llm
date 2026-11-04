/*
 * @test
 * @summary Verifies forEach delegation behavior of the unmodifiable collection wrapper returned by Collections.unmodifiableCollection
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testVisitsElementsInIterationOrder() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Collection<String> unmod = Collections.unmodifiableCollection(list);
        StringBuilder sb = new StringBuilder();
        unmod.forEach(s -> sb.append(s));
        check(sb.toString().equals("abc"), "expected abc but was " + sb);
    }

    static void testEmptyCollectionInvokesNoAction() {
        Collection<String> unmod = Collections.unmodifiableCollection(new ArrayList<String>());
        int[] calls = {0};
        unmod.forEach(s -> calls[0]++);
        check(calls[0] == 0, "expected no calls but got " + calls[0]);
    }

    static void testReflectsUnderlyingCollectionState() {
        List<Integer> list = new ArrayList<>();
        Collection<Integer> unmod = Collections.unmodifiableCollection(list);
        list.add(1);
        list.add(2);
        int[] sum = {0};
        unmod.forEach(i -> sum[0] += i);
        check(sum[0] == 3, "expected sum 3 but was " + sum[0]);
    }

    static void testCountMatchesSize() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("x" + i);
        }
        Collection<String> unmod = Collections.unmodifiableCollection(list);
        int[] count = {0};
        unmod.forEach(s -> count[0]++);
        check(count[0] == unmod.size(), "expected " + unmod.size() + " but was " + count[0]);
    }

    static void testExceptionFromActionPropagates() {
        List<String> list = new ArrayList<>();
        list.add("only");
        Collection<String> unmod = Collections.unmodifiableCollection(list);
        boolean thrown = false;
        try {
            unmod.forEach(s -> {
                throw new IllegalStateException("boom");
            });
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "expected IllegalStateException from action to propagate");
    }

    public static void main(String[] args) {
        testVisitsElementsInIterationOrder();
        testEmptyCollectionInvokesNoAction();
        testReflectsUnderlyingCollectionState();
        testCountMatchesSize();
        testExceptionFromActionPropagates();
    }
}
