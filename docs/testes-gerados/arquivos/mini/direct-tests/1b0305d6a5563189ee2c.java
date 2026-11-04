/*
 * @test
 * @summary Test Collections.removeIf throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collections c = new Collections() { // anonymous subclass to call instance method
            @Override
            public boolean removeIf(Predicate<? super Object> filter) {
                return super.removeIf(filter);
            }
        };

        boolean thrown = false;
        try {
            c.removeIf(new Predicate<Object>() {
                public boolean test(Object o) {
                    return true;
                }
            });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
    }
}
