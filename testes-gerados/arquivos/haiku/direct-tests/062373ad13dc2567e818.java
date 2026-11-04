/*
 * @test
 * @summary ReplaceableUCharacterIterator(String) constructor: non-null input yields an instance, null input throws IllegalArgumentException
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED ReplaceableUCharacterIteratorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.ReplaceableUCharacterIterator");
        Constructor<?> ctor = c.getConstructor(String.class);

        Object it = ctor.newInstance(new Object[]{"abc"});
        if (it == null || !c.isInstance(it)) {
            throw new RuntimeException("Expected an instance for non-null String");
        }

        Object empty = ctor.newInstance(new Object[]{""});
        if (empty == null || !c.isInstance(empty)) {
            throw new RuntimeException("Expected an instance for empty String");
        }

        try {
            ctor.newInstance(new Object[]{null});
            throw new RuntimeException("Expected IllegalArgumentException for null String");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("Wrong cause for null String: " + e.getCause(), e.getCause());
            }
        }
    }
}
