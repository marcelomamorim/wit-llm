/*
 * @test
 * @summary Test Collections.forEach method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Objects;
import java.util.function.BiConsumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNoOp();
    }

    static void testForEachNullAction() {
        Collections<Object, Object> c = new Collections<Object, Object>() {};
        try {
            c.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachNoOp() {
        Collections<Object, Object> c = new Collections<Object, Object>() {};
        final boolean[] called = {false};
        BiConsumer<Object, Object> action = new BiConsumer<Object, Object>() {
            public void accept(Object k, Object v) {
                called[0] = true;
            }
        };
        c.forEach(action);
        if (called[0]) {
            throw new RuntimeException("Action should not be called as method is empty");
        }
    }
}
