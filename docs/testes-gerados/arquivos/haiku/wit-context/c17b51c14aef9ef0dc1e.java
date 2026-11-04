/*
 * @test
 * @summary Witup-derived checks for Collections.nCopies(...).subList(int, int) exception and boundary behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> l = Collections.nCopies(5, "a");

        check(l.subList(1, 4).size() == 3, "subList(1,4) size");
        check("a".equals(l.subList(1, 4).get(0)), "subList(1,4) element");
        check(l.subList(0, 0).isEmpty(), "subList(0,0) empty");
        check(l.subList(5, 5).isEmpty(), "subList(5,5) empty at end");
        check(l.subList(0, 5).size() == 5, "subList(0,n) full size");

        expect(IndexOutOfBoundsException.class, () -> l.subList(-1, 2), "fromIndex < 0");
        expect(IndexOutOfBoundsException.class, () -> l.subList(0, 6), "toIndex > n");
        expect(IndexOutOfBoundsException.class, () -> l.subList(-3, 99), "fromIndex < 0 and toIndex > n");
        expect(IllegalArgumentException.class, () -> l.subList(3, 2), "fromIndex > toIndex");
    }

    interface Action {
        void run();
    }

    static void expect(Class<? extends RuntimeException> type, Action action, String msg) {
        try {
            action.run();
        } catch (RuntimeException e) {
            if (!type.isInstance(e)) {
                throw new RuntimeException(msg + ": unexpected exception " + e, e);
            }
            return;
        }
        throw new RuntimeException(msg + ": no exception thrown");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
