/*
 * @test
 * @summary WITUP-derived tests for Vector iterator forEachRemaining (null action, remaining elements, comodification)
 * @run main VectorWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyVectorInvokesNoCallback();
        testVisitsRemainingElementsAfterNext();
        testConcurrentModificationDetected();
    }

    static void testNullActionThrowsNPE() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        Iterator<String> it = v.iterator();
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }

    static void testEmptyVectorInvokesNoCallback() {
        Vector<String> v = new Vector<>();
        List<String> seen = new ArrayList<>();
        v.iterator().forEachRemaining(seen::add);
        if (!seen.isEmpty()) {
            throw new AssertionError("unexpected elements: " + seen);
        }
    }

    static void testVisitsRemainingElementsAfterNext() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c", "d"));
        Iterator<String> it = v.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("unexpected first element: " + first);
        }
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!Arrays.asList("b", "c", "d").equals(seen)) {
            throw new AssertionError("unexpected remaining elements: " + seen);
        }
        if (it.hasNext()) {
            throw new AssertionError("iterator should be exhausted");
        }
    }

    static void testConcurrentModificationDetected() {
        Vector<String> v = new Vector<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = v.iterator();
        v.add("d");
        try {
            it.forEachRemaining(x -> { });
        } catch (ConcurrentModificationException expected) {
            return;
        }
        throw new AssertionError("expected ConcurrentModificationException");
    }
}
