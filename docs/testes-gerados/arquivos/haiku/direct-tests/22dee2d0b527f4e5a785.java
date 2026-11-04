/*
 * @test
 * @summary getSession must reject null ids and return null for unknown ids through the public SSLSessionContext API
 * @run main SSLSessionContextImplWitupTest
 */

import java.util.Enumeration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;

public class SSLSessionContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSessionContext ctx = SSLContext.getInstance("TLS").getServerSessionContext();

        boolean npeThrown = false;
        try {
            ctx.getSession(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("expected NullPointerException for null session id");
        }

        if (ctx.getSession(new byte[]{1, 2, 3}) != null) {
            throw new RuntimeException("expected null for unknown session id");
        }

        Enumeration<byte[]> ids = ctx.getIds();
        if (ids.hasMoreElements()) {
            throw new RuntimeException("expected no session ids in a fresh context");
        }
    }
}
