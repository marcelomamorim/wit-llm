/*
 * @test
 * @summary Behavioral checks for forEachRemaining on spliterators of a RandomAccess AbstractList subclass
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.Spliterator;

public class AbstractListWitupTest {

    static class RA extends AbstractList<String> implements RandomAccess {
        private final String[] data;

        RA(String... d) {
            data = d;
        }

        public String get(int i) {
            return data[i];
        }

        public int size() {
            return data.length;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        testVisitsAllInOrder();
        testNullActionThrowsNPE();
        testExhaustedAfterwards();
        testEmptyList();
        testPartialConsumptionThenRest();
    }

    static void testVisitsAllInOrder() {
        List<String> out = new ArrayList<>();
        RA l = new RA("a", "b", "c");
        l.spliterator().forEachRemaining(s -> out.add(s));
        check(out.equals(Arrays.asList("a", "b", "c")), "expected [a, b, c] but was " + out);
    }

    static void testNullActionThrowsNPE() {
        boolean thrown = false;
        try {
            new RA("a").spliterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testExhaustedAfterwards() {
        List<String> out = new ArrayList<>();
        Spliterator<String> sp = new RA("x", "y").spliterator();
        sp.forEachRemaining(s -> out.add(s));
        check(out.size() == 2, "expected 2 elements but was " + out.size());
        boolean advanced = sp.tryAdvance(s -> out.add(s));
        check(!advanced, "spliterator should be exhausted after forEachRemaining");
        check(out.size() == 2, "no extra element should be visited after exhaustion");
    }

    static void testEmptyList() {
        List<String> out = new ArrayList<>();
        new RA().spliterator().forEachRemaining(s -> out.add(s));
        check(out.isEmpty(), "empty list should visit no elements");
    }

    static void testPartialConsumptionThenRest() {
        List<String> out = new ArrayList<>();
        Spliterator<String> sp = new RA("a", "b", "c").spliterator();
        boolean first = sp.tryAdvance(s -> out.add(s));
        check(first, "tryAdvance should consume the first element");
        sp.forEachRemaining(s -> out.add(s));
        check(out.equals(Arrays.asList("a", "b", "c")), "expected [a, b, c] but was " + out);
    }
}
