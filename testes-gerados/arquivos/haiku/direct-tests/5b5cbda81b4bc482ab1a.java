/*
 * @test
 * @summary Verify FtpClient.getSystem returns the SYST reply text without the reply code
 * @modules java.base/sun.net.ftp java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpClientWitupTest {

    static volatile Throwable serverError;

    public static void main(String[] args) throws Exception {
        try (ServerSocket ss = new ServerSocket()) {
            ss.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            Thread t = new Thread(() -> {
                try (Socket s = ss.accept();
                     BufferedReader r = new BufferedReader(
                         new InputStreamReader(s.getInputStream(), "ISO-8859-1"));
                     OutputStream o = s.getOutputStream()) {
                    o.write("220 Ready\r\n".getBytes("ISO-8859-1"));
                    o.flush();
                    String cmd = r.readLine();
                    if (!"SYST".equals(cmd)) {
                        throw new IllegalStateException("unexpected command: " + cmd);
                    }
                    o.write("215 UNIX Type: L8\r\n".getBytes("ISO-8859-1"));
                    o.flush();
                } catch (Throwable e) {
                    serverError = e;
                }
            });
            t.start();

            sun.net.ftp.FtpClient client = sun.net.ftp.FtpClient.create();
            try {
                client.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), ss.getLocalPort()));
                String sys = ((sun.net.ftp.impl.FtpClient) client).getSystem();
                if (!"UNIX Type: L8".equals(sys)) {
                    throw new RuntimeException("unexpected system: [" + sys + "]");
                }
            } finally {
                client.close();
            }
            t.join();
            if (serverError != null) {
                throw new RuntimeException("server failure", serverError);
            }
        }
    }
}
