/*
 * @test
 * @summary Witup-style checks for Vector iterator remove() preconditions and observable effects
 * @run main VectorWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        removeBeforeNextThrowsIllegalState();
        removeAfterNextRemovesCurrentElement();
        secondRemoveWithoutNextThrowsIllegalState();
        removeOnEmptyVectorThrowsIllegalState();
        removeAfterStructuralModificationThrowsConcurrentModification();
    }

    static void removeBeforeNextThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        Iterator<String> it = v.iterator();
        expectIllegalState(it);
        check(v.size() == 1 && "a".equals(v.get(0)), "vector must be unchanged");
    }

    static void removeAfterNextRemovesCurrentElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        Iterator<String> it = v.iterator();
        check("a".equals(it.next()), "next() should return first element");
        it.remove();
        check(v.size() == 2, "size should be 2 after remove");
        check("b".equals(v.get(0)) && "c".equals(v.get(1)), "remaining elements incorrect");
    }

    static void secondRemoveWithoutNextThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        it.next();
        it.remove();
        expectIllegalState(it);
        check(v.size() == 1 && "b".equals(v.get(0)), "vector state incorrect after failed remove");
    }

    static void removeOnEmptyVectorThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        Iterator<String> it = v.iterator();
        expectIllegalState(it);
        check(v.isEmpty(), "empty vector must stay empty");
    }

    static void removeAfterStructuralModificationThrowsConcurrentModification() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        it.next();
        v.add("c");
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException");
    }

    static void expectIllegalState(Iterator<String> it) {
        try {
            it.remove();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
