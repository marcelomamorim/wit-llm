/*
 * @test
 * @summary removeValue on the SSLSession exposed by an SSLEngine before handshake (SSLSessionImpl.nullSession)
 * @run main SSLSessionImplWitupTest
 */
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;

public class SSLSessionImplWitupTest {

    static class Listener implements SSLSessionBindingListener {
        int boundCount = 0;
        int unboundCount = 0;
        SSLSessionBindingEvent lastUnbound = null;

        public void valueBound(SSLSessionBindingEvent e) {
            boundCount++;
        }

        public void valueUnbound(SSLSessionBindingEvent e) {
            unboundCount++;
            lastUnbound = e;
        }
    }

    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, null, null);
        SSLEngine engine = ctx.createSSLEngine();
        SSLSession session = engine.getSession();

        boolean iaeThrown = false;
        try {
            session.removeValue(null);
        } catch (IllegalArgumentException expected) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new RuntimeException("removeValue(null) must throw IllegalArgumentException");
        }

        Listener l = new Listener();
        session.putValue("k", l);
        if (l.boundCount != 1) {
            throw new RuntimeException("expected valueBound once, got " + l.boundCount);
        }

        session.removeValue("k");
        if (l.unboundCount != 1) {
            throw new RuntimeException("expected valueUnbound once, got " + l.unboundCount);
        }
        if (!"k".equals(l.lastUnbound.getName())) {
            throw new RuntimeException("unexpected event name: " + l.lastUnbound.getName());
        }
        if (l.lastUnbound.getSession() != session) {
            throw new RuntimeException("event session does not match");
        }
        if (session.getValue("k") != null) {
            throw new RuntimeException("value still bound after removeValue");
        }

        session.removeValue("k");
        if (l.unboundCount != 1) {
            throw new RuntimeException("valueUnbound must not fire for absent key");
        }

        session.removeValue("missing");

        session.putValue("s", "plain");
        session.removeValue("s");
        if (session.getValue("s") != null) {
            throw new RuntimeException("non-listener value still bound after removeValue");
        }
    }
}
