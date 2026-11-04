/*
 * @test
 * @summary Exercises the public checkedCollection factory, which delegates to the package-private CheckedCollection constructor (null guards and element type checks)
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullCollectionRejected();
        testNullTypeRejected();
        testWrongTypeRejectedOnAdd();
        testCorrectTypeAccepted();
    }

    static void testNullCollectionRejected() {
        try {
            Collections.checkedCollection(null, String.class);
            throw new AssertionError("expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullTypeRejected() {
        Collection<String> c = new ArrayList<String>();
        try {
            Collections.checkedCollection(c, null);
            throw new AssertionError("expected NullPointerException for null type");
        } catch (NullPointerException expected) {
        }
    }

    @SuppressWarnings("unchecked")
    static void testWrongTypeRejectedOnAdd() {
        Collection<String> cs = Collections.checkedCollection(new ArrayList<String>(), String.class);
        try {
            ((Collection) cs).add(Integer.valueOf(1));
            throw new AssertionError("expected ClassCastException for wrong element type");
        } catch (ClassCastException expected) {
        }
        if (!cs.isEmpty()) {
            throw new AssertionError("rejected element must not be stored");
        }
    }

    static void testCorrectTypeAccepted() {
        Collection<String> cs = Collections.checkedCollection(new ArrayList<String>(), String.class);
        if (!cs.add("a")) {
            throw new AssertionError("add of correct type should return true");
        }
        if (cs.size() != 1 || !cs.contains("a")) {
            throw new AssertionError("collection should contain the added element");
        }
    }
}
