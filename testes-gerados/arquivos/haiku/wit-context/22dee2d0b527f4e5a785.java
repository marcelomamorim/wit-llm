/*
 * @test
 * @summary Checks public SSLSessionContext.getSession behavior for null and unknown session ids
 * @run main SSLSessionContextImplWitupTest
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

public class SSLSessionContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSessionContext ctx = SSLContext.getInstance("TLS").getClientSessionContext();

        boolean thrown = false;
        try {
            ctx.getSession(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null session id");
        }

        SSLSession session = ctx.getSession(new byte[] {1, 2, 3, 4});
        if (session != null) {
            throw new AssertionError("expected null for unknown session id");
        }
    }
}
