/*
 * @test
 * @summary Verify that AdaptableX509CertSelector.setSubjectKeyIdentifier always throws IllegalArgumentException
 * @modules java.base/sun.security.provider.certpath
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED AdaptableX509CertSelectorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.AdaptableX509CertSelector");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object selector = ctor.newInstance();
        Method m = c.getMethod("setSubjectKeyIdentifier", byte[].class);
        m.setAccessible(true);

        expectIllegalArgument(m, selector, new byte[]{1, 2, 3});
        expectIllegalArgument(m, selector, new byte[0]);
        expectIllegalArgument(m, selector, null);

        System.out.println("PASS");
    }

    private static void expectIllegalArgument(Method m, Object selector, byte[] arg) {
        try {
            m.invoke(selector, (Object) arg);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("expected IllegalArgumentException was not thrown");
    }
}
