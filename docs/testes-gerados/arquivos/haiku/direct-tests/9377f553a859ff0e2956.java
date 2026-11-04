/*
 * @test
 * @summary Verifies FtpClient.noop() sends NOOP and returns the client on a positive reply and fails on a negative reply
 * @modules java.base/sun.net.ftp
 * @modules java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import sun.net.ftp.FtpProtocolException;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testNoopPositiveReply();
        testNoopNegativeReply();
    }

    static void testNoopPositiveReply() throws Exception {
        String[] received = new String[1];
        Throwable[] failure = new Throwable[1];
        try (ServerSocket ss = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            Thread t = serve(ss, "200 OK", received, failure);
            sun.net.ftp.FtpClient c = sun.net.ftp.FtpClient.create();
            try {
                c.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), ss.getLocalPort()));
                sun.net.ftp.FtpClient result = c.noop();
                if (result != c) {
                    throw new RuntimeException("noop() did not return this client");
                }
            } finally {
                c.close();
            }
            t.join();
        }
        if (failure[0] != null) {
            throw new RuntimeException(failure[0]);
        }
        if (!"NOOP".equals(received[0])) {
            throw new RuntimeException("Expected NOOP command but got: " + received[0]);
        }
    }

    static void testNoopNegativeReply() throws Exception {
        String[] received = new String[1];
        Throwable[] failure = new Throwable[1];
        boolean protocolException = false;
        try (ServerSocket ss = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            Thread t = serve(ss, "500 Error", received, failure);
            sun.net.ftp.FtpClient c = sun.net.ftp.FtpClient.create();
            try {
                c.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), ss.getLocalPort()));
                c.noop();
            } catch (FtpProtocolException e) {
                protocolException = true;
            } finally {
                c.close();
            }
            t.join();
        }
        if (failure[0] != null) {
            throw new RuntimeException(failure[0]);
        }
        if (!protocolException) {
            throw new RuntimeException("Expected FtpProtocolException for negative NOOP reply");
        }
        if (!"NOOP".equals(received[0])) {
            throw new RuntimeException("Expected NOOP command but got: " + received[0]);
        }
    }

    static Thread serve(ServerSocket ss, String reply, String[] received, Throwable[] failure) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try (Socket s = ss.accept()) {
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(s.getInputStream(), "ISO-8859-1"));
                    PrintWriter w = new PrintWriter(
                            new OutputStreamWriter(s.getOutputStream(), "ISO-8859-1"), true);
                    w.print("220 Welcome\r\n");
                    w.flush();
                    received[0] = r.readLine();
                    w.print(reply + "\r\n");
                    w.flush();
                } catch (IOException | RuntimeException e) {
                    failure[0] = e;
                }
            }
        });
        t.start();
        return t;
    }
}
