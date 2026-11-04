/*
 * @test
 * @summary Test forEachRemaining method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static class TestIterator<E> {
        private boolean hasNext;
        private E e;

        TestIterator(boolean hasNext, E e) {
            this.hasNext = hasNext;
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
        // Test 1: hasNext true, action should be called once with element
        final StringBuilder sb1 = new StringBuilder();
        TestIterator<String> it1 = new TestIterator<>(true, "test");
        it1.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb1.append(s);
            }
        });
        if (!"test".equals(sb1.toString())) {
            throw new Error("Test 1 failed: action not called correctly");
        }

        // Test 2: hasNext false, action should not be called
        final StringBuilder sb2 = new StringBuilder();
        TestIterator<String> it2 = new TestIterator<>(false, "fail");
        it2.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb2.append(s);
            }
        });
        if (sb2.length() != 0) {
            throw new Error("Test 2 failed: action should not be called when hasNext is false");
        }

        // Test 3: action is null, should throw NullPointerException
        TestIterator<String> it3 = new TestIterator<>(true, "x");
        boolean npeThrown = false;
        try {
            it3.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("Test 3 failed: NullPointerException not thrown when action is null");
        }
    }
}
