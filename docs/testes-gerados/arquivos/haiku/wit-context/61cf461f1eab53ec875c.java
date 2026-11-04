/*
 * @test
 * @summary Checks Vector.listIterator(int) index validation and boundary behavior
 * @run main VectorWitupTest
 */
import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyVectorBoundaries();
        testPopulatedVectorBoundaries();
        testNegativeAndExtremeIndexes();
    }

    static void testEmptyVectorBoundaries() {
        Vector<Integer> v = new Vector<>();
        ListIterator<Integer> it = v.listIterator(0);
        if (it.hasNext()) {
            throw new RuntimeException("empty vector iterator at 0 must have no next");
        }
        if (it.nextIndex() != 0) {
            throw new RuntimeException("nextIndex mismatch for empty vector");
        }
        expectIndexOutOfBounds(v, 1);
    }

    static void testPopulatedVectorBoundaries() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);

        ListIterator<Integer> atEnd = v.listIterator(v.size());
        if (atEnd.hasNext()) {
            throw new RuntimeException("iterator at size must have no next");
        }
        if (atEnd.previousIndex() != 1) {
            throw new RuntimeException("previousIndex mismatch at end");
        }

        ListIterator<Integer> atStart = v.listIterator(0);
        if (!atStart.hasNext() || atStart.next().intValue() != 1) {
            throw new RuntimeException("iterator at 0 must start at first element");
        }

        expectIndexOutOfBounds(v, v.size() + 1);
        expectIndexOutOfBounds(v, 3);
    }

    static void testNegativeAndExtremeIndexes() {
        Vector<Integer> v = new Vector<>();
        v.add(7);
        expectIndexOutOfBounds(v, -1);
        expectIndexOutOfBounds(v, Integer.MIN_VALUE);
        expectIndexOutOfBounds(v, Integer.MAX_VALUE);
    }

    static void expectIndexOutOfBounds(Vector<Integer> v, int index) {
        try {
            v.listIterator(index);
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }
}
