/*
 * @test
 * @summary AdaptableX509CertSelector.setSerialNumber must always throw IllegalArgumentException and leave the serial number unchanged
 * @modules java.base/sun.security.provider.certpath
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED AdaptableX509CertSelectorWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.cert.X509CertSelector;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.AdaptableX509CertSelector");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object sel = ctor.newInstance();

        Method m = X509CertSelector.class.getMethod("setSerialNumber", BigInteger.class);

        expectIllegalArgument(m, sel, BigInteger.ONE);
        expectIllegalArgument(m, sel, BigInteger.valueOf(-5));
        expectIllegalArgument(m, sel, null);

        X509CertSelector xsel = (X509CertSelector) sel;
        if (xsel.getSerialNumber() != null) {
            throw new RuntimeException("serial number changed after rejected call");
        }
    }

    private static void expectIllegalArgument(Method m, Object target, BigInteger arg) throws Exception {
        try {
            m.invoke(target, arg);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause", e.getCause());
            }
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown");
    }
}
