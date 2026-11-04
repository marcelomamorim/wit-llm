/*
 * @test
 * @summary White-box-free checks of ArrayList spliterator forEachRemaining: null action, remaining elements, and concurrent modification
 * @run main ArrayListWitupTest
 */
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Spliterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsRemainingElementsInOrder();
        testConcurrentModificationDetected();
        System.out.println("PASS");
    }

    static void testNullActionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Spliterator<String> s = list.spliterator();
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }

    static void testVisitsRemainingElementsInOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> s = list.spliterator();
        s.tryAdvance(x -> { });
        ArrayList<Integer> seen = new ArrayList<>();
        s.forEachRemaining(seen::add);
        if (!seen.equals(java.util.Arrays.asList(2, 3))) {
            throw new AssertionError("unexpected remaining elements: " + seen);
        }
    }

    static void testConcurrentModificationDetected() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> s = list.spliterator();
        try {
            s.forEachRemaining(x -> {
                if (Integer.valueOf(1).equals(x)) {
                    list.add(4);
                }
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new AssertionError("expected ConcurrentModificationException");
    }
}
