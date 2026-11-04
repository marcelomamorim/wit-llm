/*
 * @test
 * @summary Test java.util.Collections.forEachRemaining method for null action and normal execution
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {

    static class TestIterator<E> {
        private boolean hasNext = true;
        private final E e;

        TestIterator(E e) {
            this.e = e;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            if (hasNext) {
                hasNext = false;
                action.accept(e);
            }
        }
    }

    public static void main(String[] args) {
        // Test 1: forEachRemaining throws NullPointerException if action is null
        TestIterator<String> it1 = new TestIterator<>("test");
        boolean npeThrown = false;
        try {
            it1.forEachRemaining(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when action is null");
        }

        // Test 2: forEachRemaining calls action.accept once if hasNext is true
        final boolean[] called = {false};
        TestIterator<String> it2 = new TestIterator<>("hello");
        it2.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                if (!"hello".equals(s)) {
                    throw new RuntimeException("Unexpected element passed to accept");
                }
                called[0] = true;
            }
        });
        if (!called[0]) {
            throw new RuntimeException("Expected action.accept to be called once");
        }

        // Test 3: forEachRemaining does not call action.accept if hasNext is false
        final boolean[] called2 = {false};
        TestIterator<String> it3 = new TestIterator<>("ignored");
        it3.hasNext = false; // simulate no next element
        it3.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                called2[0] = true;
            }
        });
        if (called2[0]) {
            throw new RuntimeException("Expected action.accept not to be called when hasNext is false");
        }
    }
}
