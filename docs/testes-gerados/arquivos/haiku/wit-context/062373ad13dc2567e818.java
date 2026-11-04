/*
 * @test
 * @summary Verifies null-argument rejection and basic length behavior of ReplaceableUCharacterIterator constructors
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED ReplaceableUCharacterIteratorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReplaceableUCharacterIteratorWitupTest {

    private static final String CLASS_NAME = "sun.text.normalizer.ReplaceableUCharacterIterator";

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName(CLASS_NAME);

        Constructor<?> strCtor = c.getDeclaredConstructor(String.class);
        strCtor.setAccessible(true);

        Constructor<?> bufCtor = c.getDeclaredConstructor(StringBuffer.class);
        bufCtor.setAccessible(true);

        Method getLength = c.getMethod("getLength");
        getLength.setAccessible(true);

        expectIllegalArgument(strCtor, "String null");
        expectIllegalArgument(bufCtor, "StringBuffer null");

        Object fromString = strCtor.newInstance("abc");
        if (fromString == null) {
            throw new RuntimeException("String constructor returned null");
        }
        int len1 = (Integer) getLength.invoke(fromString);
        if (len1 != 3) {
            throw new RuntimeException("Expected length 3 but was " + len1);
        }

        Object fromBuffer = bufCtor.newInstance(new StringBuffer("xy"));
        if (fromBuffer == null) {
            throw new RuntimeException("StringBuffer constructor returned null");
        }
        int len2 = (Integer) getLength.invoke(fromBuffer);
        if (len2 != 2) {
            throw new RuntimeException("Expected length 2 but was " + len2);
        }

        Object empty = strCtor.newInstance("");
        int len3 = (Integer) getLength.invoke(empty);
        if (len3 != 0) {
            throw new RuntimeException("Expected length 0 but was " + len3);
        }
    }

    private static void expectIllegalArgument(Constructor<?> ctor, String label) throws Exception {
        try {
            ctor.newInstance((Object) null);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException(label + ": unexpected cause " + cause, e);
            }
            return;
        }
        throw new RuntimeException(label + ": expected IllegalArgumentException");
    }
}
