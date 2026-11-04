/*
 * @test
 * @summary Verifies the StringBuffer constructor of ReplaceableUCharacterIterator: null rejection and observable state for non-null input
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED ReplaceableUCharacterIteratorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReplaceableUCharacterIteratorWitupTest {

    static final String CLS = "sun.text.normalizer.ReplaceableUCharacterIterator";

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName(CLS);
        Constructor<?> sbCtor = c.getConstructor(StringBuffer.class);
        sbCtor.setAccessible(true);

        try {
            sbCtor.newInstance(new Object[]{null});
            throw new RuntimeException("expected IllegalArgumentException for null buffer");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause());
            }
        }

        Method getLength = c.getMethod("getLength");
        getLength.setAccessible(true);
        Method current = c.getMethod("current");
        current.setAccessible(true);

        Object abc = sbCtor.newInstance(new Object[]{new StringBuffer("abc")});
        int len = (Integer) getLength.invoke(abc);
        if (len != 3) {
            throw new RuntimeException("getLength expected 3 but was " + len);
        }
        int cur = (Integer) current.invoke(abc);
        if (cur != 'a') {
            throw new RuntimeException("current expected 'a' but was " + cur);
        }

        Object empty = sbCtor.newInstance(new Object[]{new StringBuffer()});
        int emptyLen = (Integer) getLength.invoke(empty);
        if (emptyLen != 0) {
            throw new RuntimeException("empty getLength expected 0 but was " + emptyLen);
        }
    }
}
