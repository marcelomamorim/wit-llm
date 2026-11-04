/*
 * @test
 * @summary Verify that the default SSLContext is available and reports the Default protocol when no keystore property is set
 * @run main SSLContextImplWitupTest
 */

import javax.net.ssl.SSLContext;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        System.clearProperty("javax.net.ssl.keyStore");
        SSLContext ctx = SSLContext.getDefault();
        if (ctx == null) {
            throw new RuntimeException("getDefault() returned null");
        }
        if (!"Default".equals(ctx.getProtocol())) {
            throw new RuntimeException("Unexpected protocol: " + ctx.getProtocol());
        }
    }
}
