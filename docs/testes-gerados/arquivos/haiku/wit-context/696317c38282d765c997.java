/*
 * @test
 * @summary Verifies that the iterator returned by Arrays.asList throws NoSuchElementException when next() is called past the end
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testIteratesInOrder();
        testNextPastEndThrows();
        testEmptyArrayThrows();
        testRepeatedCallsAfterEndStillThrow();
        System.out.println("PASS");
    }

    static void testIteratesInOrder() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        for (int expected = 1; expected <= 3; expected++) {
            if (!it.hasNext()) {
                throw new RuntimeException("hasNext() returned false too early");
            }
            Integer v = it.next();
            if (v.intValue() != expected) {
                throw new RuntimeException("unexpected element: " + v);
            }
        }
        if (it.hasNext()) {
            throw new RuntimeException("hasNext() returned true after the end");
        }
    }

    static void testNextPastEndThrows() {
        Iterator<Integer> it = Arrays.asList(1).iterator();
        it.next();
        expectNoSuchElement(it);
    }

    static void testEmptyArrayThrows() {
        Iterator<Integer> it = Arrays.asList(new Integer[0]).iterator();
        expectNoSuchElement(it);
    }

    static void testRepeatedCallsAfterEndStillThrow() {
        Iterator<Integer> it = Arrays.asList(7).iterator();
        it.next();
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void expectNoSuchElement(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }
}
