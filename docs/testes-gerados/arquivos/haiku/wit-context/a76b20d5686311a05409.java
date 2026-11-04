/*
 * @test
 * @summary Verify that the public DefaultSSLContext constructor succeeds and yields distinct instances
 * @modules java.base/sun.security.ssl
 * @run main SSLContextImplWitupTest
 */

import sun.security.ssl.SSLContextImpl;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLContextImpl.DefaultSSLContext ctx1 = new SSLContextImpl.DefaultSSLContext();
        if (ctx1 == null) {
            throw new RuntimeException("DefaultSSLContext constructor returned null");
        }

        SSLContextImpl.DefaultSSLContext ctx2 = new SSLContextImpl.DefaultSSLContext();
        if (ctx2 == null) {
            throw new RuntimeException("Second DefaultSSLContext constructor returned null");
        }

        if (ctx1 == ctx2) {
            throw new RuntimeException("Constructor returned the same instance twice");
        }
    }
}
