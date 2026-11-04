/*
 * @test
 * @summary Test that Collections.replaceAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collections c = new Collections() {
            @Override
            public void replaceAll(BiFunction function) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            c.replaceAll(new BiFunction<Object, Object, Object>() {
                public Object apply(Object k, Object v) {
                    return v;
                }
            });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        }
    }
}
