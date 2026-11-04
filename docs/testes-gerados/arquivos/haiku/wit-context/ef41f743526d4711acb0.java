/*
 * @test
 * @summary Verifies that Iterator.remove() on a jumbo EnumSet throws IllegalStateException when no element was returned by next()
 * @run main JumboEnumSetWitupTest
 */
import java.util.EnumSet;
import java.util.Iterator;

public class JumboEnumSetWitupTest {
    enum Big {
        A0, A1, A2, A3, A4, A5, A6, A7,
        A8, A9, A10, A11, A12, A13, A14, A15,
        A16, A17, A18, A19, A20, A21, A22, A23,
        A24, A25, A26, A27, A28, A29, A30, A31,
        A32, A33, A34, A35, A36, A37, A38, A39,
        A40, A41, A42, A43, A44, A45, A46, A47,
        A48, A49, A50, A51, A52, A53, A54, A55,
        A56, A57, A58, A59, A60, A61, A62, A63,
        A64
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        EnumSet<Big> set = EnumSet.noneOf(Big.class);
        set.add(Big.A0);
        set.add(Big.A64);

        Iterator<Big> it = set.iterator();

        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(set.size() == 2, "set must be unchanged after failed remove()");

        check(it.next() == Big.A0, "first element must be A0");
        it.remove();
        check(set.size() == 1, "size must be 1 after removing A0");
        check(!set.contains(Big.A0), "A0 must be removed");
        check(set.contains(Big.A64), "A64 must remain");

        thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(set.size() == 1, "set must be unchanged after second failed remove()");

        check(it.next() == Big.A64, "next element must be A64");
    }
}
