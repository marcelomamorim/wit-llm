/*
 * @test
 * @summary Structural check of AdaptableX509CertSelector.setSerialNumber(BigInteger) declaration
 * @run main AdaptableX509CertSelectorWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.AdaptableX509CertSelector");
        Method m = c.getDeclaredMethod("setSerialNumber", BigInteger.class);

        if (!Modifier.isPublic(m.getModifiers())) {
            throw new AssertionError("setSerialNumber must be public");
        }
        if (m.getReturnType() != void.class) {
            throw new AssertionError("setSerialNumber must return void");
        }
        if (m.getParameterCount() != 1) {
            throw new AssertionError("setSerialNumber must take one parameter");
        }
        if (m.getParameterTypes()[0] != BigInteger.class) {
            throw new AssertionError("setSerialNumber parameter must be BigInteger");
        }
    }
}
