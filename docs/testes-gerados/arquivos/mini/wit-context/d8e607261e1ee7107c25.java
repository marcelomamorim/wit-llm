/*
 * @test
 * @summary Test CopyOnWriteArrayList.COWIterator.forEachRemaining behavior and null action rejection
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        CopyOnWriteArrayList.COWIterator<String> it = list.new COWIterator<>(0);
        boolean npeThrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null action to forEachRemaining");
        }
    }

    static void testForEachRemainingNormal() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"x", "y", "z"});
        CopyOnWriteArrayList.COWIterator<String> it = list.new COWIterator<>(0);
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        it.forEachRemaining(action);
        String result = sb.toString();
        if (!"xyz".equals(result)) {
            throw new AssertionError("forEachRemaining did not process all elements correctly, got: " + result);
        }
        // Subsequent call should do nothing as cursor advanced to end
        sb.setLength(0);
        it.forEachRemaining(action);
        if (sb.length() != 0) {
            throw new AssertionError("forEachRemaining should not process elements after cursor reached end");
        }
    }

}