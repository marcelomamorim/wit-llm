/*
 * @test
 * @summary Verify Collections.emptyIterator().forEachRemaining rejects null action and does not invoke consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Iterator<String> it = Collections.emptyIterator();

        boolean npe = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null action");
        }

        final int[] calls = {0};
        Consumer<Object> counter = x -> calls[0]++;
        Collections.<String>emptyIterator().forEachRemaining(counter);
        if (calls[0] != 0) {
            throw new RuntimeException("consumer must not be invoked on empty iterator");
        }

        if (it.hasNext()) {
            throw new RuntimeException("empty iterator must have no elements");
        }
    }
}
