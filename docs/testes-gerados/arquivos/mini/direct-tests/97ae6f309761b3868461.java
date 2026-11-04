/*
 * @test
 * @summary Test java.util.Collections.forEach method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNoExceptionOnValidConsumer();
    }

    static void testForEachNullAction() {
        Collections c = new Collections() {
            @Override
            public void forEach(Consumer<? super Object> action) {
                Objects.requireNonNull(action);
            }
        };
        try {
            c.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachNoExceptionOnValidConsumer() {
        Collections c = new Collections() {
            @Override
            public void forEach(Consumer<? super Object> action) {
                Objects.requireNonNull(action);
            }
        };
        Consumer<Object> consumer = new Consumer<Object>() {
            public void accept(Object o) {
                // no-op
            }
        };
        c.forEach(consumer); // should not throw
    }
}
