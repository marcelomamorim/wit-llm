/*
 * @test
 * @summary Tests Collections singleton iterator forEachRemaining null check and single-element consumption
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpeAndKeepsElement();
        testForEachRemainingConsumesSingleElementOnce();
        testExhaustedIteratorDoesNotInvokeAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNpeAndKeepsElement() {
        Iterator<String> it = Collections.singleton("x").iterator();
        boolean npe = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "forEachRemaining(null) must throw NullPointerException");
        check(it.hasNext(), "element must remain available after failed null action");
    }

    static void testForEachRemainingConsumesSingleElementOnce() {
        Iterator<String> it = Collections.singleton("x").iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 1, "action must be invoked exactly once");
        check("x".equals(seen.get(0)), "action must receive the singleton element");
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }

    static void testExhaustedIteratorDoesNotInvokeAction() {
        Iterator<String> it = Collections.singleton("x").iterator();
        it.next();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "exhausted iterator must not invoke action");
        check(!it.hasNext(), "exhausted iterator must remain exhausted");
    }
}
