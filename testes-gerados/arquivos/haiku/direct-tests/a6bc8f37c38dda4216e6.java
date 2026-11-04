/*
 * @test
 * @summary Checks public observable behavior of SelectionKeyImpl.readyOps() through the Selector API
 * @run main SelectionKeyImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectionKeyImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadyOpsThrowsWhenCancelled();
        testReadyOpsZeroBeforeAnyReadiness();
        testReadyOpsReportsAcceptReadiness();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ServerSocketChannel openBoundServer() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        return ssc;
    }

    static void testReadyOpsThrowsWhenCancelled() throws Exception {
        Selector sel = Selector.open();
        ServerSocketChannel ssc = openBoundServer();
        try {
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            key.cancel();
            boolean thrown = false;
            try {
                key.readyOps();
            } catch (CancelledKeyException e) {
                thrown = true;
            }
            check(thrown, "readyOps() on cancelled key must throw CancelledKeyException");
        } finally {
            ssc.close();
            sel.close();
        }
    }

    static void testReadyOpsZeroBeforeAnyReadiness() throws Exception {
        Selector sel = Selector.open();
        ServerSocketChannel ssc = openBoundServer();
        try {
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            int selected = sel.selectNow();
            check(selected == 0, "no connection pending, selectNow must return 0");
            check(key.readyOps() == 0, "readyOps must be 0 when no operation is ready");
        } finally {
            ssc.close();
            sel.close();
        }
    }

    static void testReadyOpsReportsAcceptReadiness() throws Exception {
        Selector sel = Selector.open();
        ServerSocketChannel ssc = openBoundServer();
        SocketChannel client = null;
        SocketChannel accepted = null;
        try {
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            client = SocketChannel.open(ssc.getLocalAddress());
            int selected = sel.select(5000);
            check(selected >= 1, "expected accept readiness after client connect");
            int ready = key.readyOps();
            check((ready & SelectionKey.OP_ACCEPT) != 0,
                  "readyOps must include OP_ACCEPT after pending connection");
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
            accepted = ssc.accept();
            check(accepted != null, "accept must return the pending connection");
        } finally {
            if (accepted != null) accepted.close();
            if (client != null) client.close();
            ssc.close();
            sel.close();
        }
    }
}
