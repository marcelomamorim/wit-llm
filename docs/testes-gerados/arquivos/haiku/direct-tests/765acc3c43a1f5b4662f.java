/*
 * @test
 * @summary Verifies the delegating behavior of Collections.synchronizedCollection
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");

        Collection<String> sync = Collections.synchronizedCollection(backing);
        check(sync != null, "wrapper must not be null");
        check(sync.size() == 2, "size must reflect backing collection");
        check(sync.contains("a"), "wrapper must see existing elements");
        check(sync.toString().equals(backing.toString()), "toString must delegate");

        sync.add("c");
        check(backing.size() == 3, "add through wrapper must affect backing collection");
        check(backing.contains("c"), "added element must be visible in backing collection");

        check(sync.remove("a"), "remove through wrapper must succeed");
        check(!backing.contains("a"), "removed element must be gone from backing collection");

        Collection<String> another = Collections.synchronizedCollection(backing);
        check(another != sync, "each call must return a new wrapper");
        check(another.size() == backing.size(), "new wrapper must reflect backing state");

        boolean npe = false;
        try {
            Collections.synchronizedCollection(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null argument must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
