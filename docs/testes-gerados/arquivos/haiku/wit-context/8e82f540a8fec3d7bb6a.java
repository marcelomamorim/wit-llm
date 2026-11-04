/*
 * @test
 * @summary Verifies that the iterator adapter returned by Spliterators.iterator throws NoSuchElementException from next() when no element is available
 * @run main SpliteratorsWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testEmptySpliteratorNextThrows();
        testExhaustedNextThrowsAgain();
        testElementsInOrderThenThrows();
        System.out.println("PASS");
    }

    static void testEmptySpliteratorNextThrows() {
        Iterator<Object> it = Spliterators.iterator(Spliterators.<Object>emptySpliterator());
        if (it.hasNext()) {
            throw new RuntimeException("empty spliterator should have no next element");
        }
        expectNoSuchElement(it);
    }

    static void testExhaustedNextThrowsAgain() {
        Iterator<Object> it = Spliterators.iterator(Spliterators.<Object>emptySpliterator());
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void testElementsInOrderThenThrows() {
        List<String> list = Arrays.asList("a", "b");
        Iterator<String> it = Spliterators.iterator(list.spliterator());
        if (!it.hasNext() || !"a".equals(it.next())) {
            throw new RuntimeException("first element mismatch");
        }
        if (!it.hasNext() || !"b".equals(it.next())) {
            throw new RuntimeException("second element mismatch");
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
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
