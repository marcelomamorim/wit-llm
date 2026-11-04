/*
 * @test
 * @summary Verify that SSLContext.init with a missing javax.net.ssl.keyStore file either completes or fails with KeyManagementException, leaving a usable SSLContext
 * @run main SSLContextImplWitupTest
 */

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.KeyManagementException;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        String prop = "javax.net.ssl.keyStore";
        String old = System.getProperty(prop);
        File missing = new File("missing-keystore-" + System.nanoTime() + ".jks");
        System.setProperty(prop, missing.getAbsolutePath());
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            try {
                ctx.init(null, null, null);
            } catch (KeyManagementException e) {
                return;
            }
            if (ctx.getSocketFactory() == null) {
                throw new RuntimeException("SSLSocketFactory must not be null after init");
            }
            if (ctx.getServerSocketFactory() == null) {
                throw new RuntimeException("SSLServerSocketFactory must not be null after init");
            }
        } finally {
            if (old == null) {
                System.clearProperty(prop);
            } else {
                System.setProperty(prop, old);
            }
        }
    }
}
