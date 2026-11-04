/*
 * @test
 * @summary Checks Iterator.forEachRemaining delegation through Collections.checkedCollection(...).iterator()
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingVisitsRemainingElements();
    }

    static void testNullActionThrowsNPE() {
        Collection<String> c = Collections.checkedCollection(new ArrayList<String>(), String.class);
        c.add("a");
        Iterator<String> it = c.iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingVisitsRemainingElements() {
        Collection<String> c = Collections.checkedCollection(new ArrayList<String>(), String.class);
        c.add("a");
        c.add("b");
        c.add("c");
        Iterator<String> it = c.iterator();
        it.next();
        final List<String> out = new ArrayList<>();
        Consumer<String> sink = out::add;
        it.forEachRemaining(sink);
        if (!out.equals(java.util.Arrays.asList("b", "c"))) {
            throw new AssertionError("unexpected elements: " + out);
        }
        if (it.hasNext()) {
            throw new AssertionError("iterator should be exhausted");
        }
    }
}
