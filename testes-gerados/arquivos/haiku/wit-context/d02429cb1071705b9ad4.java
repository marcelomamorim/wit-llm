/*
 * @test
 * @summary WITUP: ReplaceableUCharacterIterator(StringBuffer) must reject null with IllegalArgumentException and accept a non-null buffer
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED ReplaceableUCharacterIteratorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.text.normalizer.ReplaceableUCharacterIterator");
        Constructor<?> ctor = cls.getConstructor(StringBuffer.class);
        ctor.setAccessible(true);

        try {
            ctor.newInstance((Object) null);
            throw new RuntimeException("expected InvocationTargetException for null StringBuffer");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
        }

        Object it = ctor.newInstance(new StringBuffer("abc"));
        if (it == null || it.getClass() != cls) {
            throw new RuntimeException("constructor did not create a ReplaceableUCharacterIterator");
        }
    }
}
