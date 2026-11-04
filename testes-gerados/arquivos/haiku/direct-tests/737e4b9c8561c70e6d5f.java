/*
 * @test
 * @summary Verifies that Collections.nCopies(...).toArray() returns a fresh Object[] filled with the copied element
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> l = Collections.nCopies(3, "x");
        Object[] a = l.toArray();
        check(a.length == 3, "length");
        check(a.getClass() == Object[].class, "type");
        for (Object o : a) {
            check("x".equals(o), "element");
        }
        a[0] = "y";
        Object[] b = l.toArray();
        check("x".equals(b[0]), "returned array must be a fresh copy");
        check("x".equals(l.get(0)), "list must be unchanged");
        Object[] empty = Collections.nCopies(0, "x").toArray();
        check(empty.length == 0, "empty length");
        Object[] nulls = Collections.nCopies(2, null).toArray();
        check(nulls.length == 2 && nulls[0] == null && nulls[1] == null, "null element");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
