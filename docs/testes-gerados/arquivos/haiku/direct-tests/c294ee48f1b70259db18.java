/*
 * @test
 * @summary SSLContext.setDefault rejects null and makes the given context the value returned by getDefault
 * @run main SSLContextWitupTest
 */

import javax.net.ssl.SSLContext;

public class SSLContextWitupTest {

    static class TestSSLContext extends SSLContext {
        TestSSLContext() {
            super(null, null, "WitupTest");
        }
    }

    public static void main(String[] args) throws Exception {
        boolean npeThrown = false;
        try {
            SSLContext.setDefault(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null context");
        }

        SSLContext ctx = new TestSSLContext();
        SSLContext.setDefault(ctx);
        if (SSLContext.getDefault() != ctx) {
            throw new RuntimeException("getDefault did not return the context set by setDefault");
        }

        SSLContext ctx2 = new TestSSLContext();
        SSLContext.setDefault(ctx2);
        if (SSLContext.getDefault() != ctx2) {
            throw new RuntimeException("getDefault did not return the latest context set by setDefault");
        }
    }
}
