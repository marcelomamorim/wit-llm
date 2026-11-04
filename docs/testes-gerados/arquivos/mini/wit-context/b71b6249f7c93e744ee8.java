/*
 * @test
 * @summary Test ArrayDeque.DeqIterator.remove() method for IllegalStateException when lastRet < 0
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        // Create an ArrayDeque and add elements
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);

        // Obtain iterator
        Iterator<Integer> it = deque.iterator();

        // Test normal remove after next()
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        Integer first = it.next();
        it.remove(); // should succeed

        // Test remove called twice without next() in between
        boolean illegalStateThrown = false;
        try {
            it.remove(); // second remove without next() should throw IllegalStateException
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        }
        if (!illegalStateThrown) {
            throw new RuntimeException("Expected IllegalStateException not thrown on second remove call");
        }

        // Test remove called before any next() call
        Iterator<Integer> it2 = deque.iterator();
        illegalStateThrown = false;
        try {
            it2.remove(); // remove before next() should throw IllegalStateException
        } catch (IllegalStateException e) {
            illegalStateThrown = true;
        }
        if (!illegalStateThrown) {
            throw new RuntimeException("Expected IllegalStateException not thrown on remove before next");
        }

        System.out.println("All tests passed.");
    }
}
