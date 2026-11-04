/*
 * @test
 * @summary Verifies forEachRemaining on the iterator returned by Collections.singleton
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<String> it = Collections.singleton("x").iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 1 && "x".equals(seen.get(0)), "first call should emit x once");
        it.forEachRemaining(seen::add);
        check(seen.size() == 1, "second call must not emit again");
        check(!it.hasNext(), "hasNext must be false after forEachRemaining");
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "next() must throw NoSuchElementException when exhausted");

        Iterator<String> it2 = Collections.singleton("y").iterator();
        boolean npe = false;
        try {
            it2.forEachRemaining(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null action must throw NullPointerException");
        check(it2.hasNext(), "null action must not consume the element");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
