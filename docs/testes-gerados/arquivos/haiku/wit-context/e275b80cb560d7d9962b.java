/*
 * @test
 * @summary AdaptableX509CertSelector.setSubjectKeyIdentifier must always reject the key identifier with IllegalArgumentException
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED AdaptableX509CertSelectorWitupTest
 */

import java.lang.reflect.Constructor;
import java.security.cert.X509CertSelector;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.AdaptableX509CertSelector");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        X509CertSelector sel = (X509CertSelector) ctor.newInstance();
        expectIllegalArgument(sel, new byte[] {1, 2, 3});
        expectIllegalArgument(sel, new byte[0]);
        expectIllegalArgument(sel, null);
    }

    static void expectIllegalArgument(X509CertSelector sel, byte[] id) {
        try {
            sel.setSubjectKeyIdentifier(id);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected");
    }
}
