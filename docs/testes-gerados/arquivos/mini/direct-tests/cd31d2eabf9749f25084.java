/*
 * @test
 * @summary Test Collections.forEach method for null action argument
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
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
}
