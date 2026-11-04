/*
 * @test
 * @summary WITUP generated test: CopyOnWriteArrayList sublist iterator next() throws NoSuchElementException when exhausted
 * @run main CopyOnWriteArrayListWitupTest
 */
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testEmptySubListNextThrows();
        testExhaustedSubListNextThrows();
        testSubListNextReturnsElementsInOrder();
    }

    static void testEmptySubListNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        List<String> sub = list.subList(1, 1);
        Iterator<String> it = sub.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("empty sublist iterator should have no next element");
        }
        expectNoSuchElement(it);
    }

    static void testExhaustedSubListNextThrows() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        List<String> sub = list.subList(0, 2);
        Iterator<String> it = sub.iterator();
        it.next();
        it.next();
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted after two elements");
        }
        expectNoSuchElement(it);
    }

    static void testSubListNextReturnsElementsInOrder() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        List<String> sub = list.subList(1, 3);
        Iterator<String> it = sub.iterator();
        check("b".equals(it.next()), "first element of subList(1,3) should be b");
        check(it.hasNext(), "iterator should have a second element");
        check("c".equals(it.next()), "second element of subList(1,3) should be c");
        check(!it.hasNext(), "iterator should be exhausted");
        expectNoSuchElement(it);
    }

    static void expectNoSuchElement(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException from next()");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
