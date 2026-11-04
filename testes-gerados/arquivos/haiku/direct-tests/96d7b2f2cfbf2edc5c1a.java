/*
 * @test
 * @summary Verify that setEnabledCipherSuites on the default SSLServerSocket updates the enabled cipher suites observed through the public API
 * @run main SSLServerSocketImplWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLServerSocketFactory factory =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket ss = (SSLServerSocket) factory.createServerSocket();
        try {
            String[] supported = ss.getSupportedCipherSuites();
            if (supported == null || supported.length == 0) {
                throw new RuntimeException("No supported cipher suites");
            }
            String[] supportedBefore = supported.clone();

            String[] one = new String[] { supported[0] };
            ss.setEnabledCipherSuites(one);
            String[] got = ss.getEnabledCipherSuites();
            if (!Arrays.equals(one, got)) {
                throw new RuntimeException("Single suite not reflected: "
                        + Arrays.toString(got));
            }

            String[] two = supported.length >= 2
                    ? new String[] { supported[0], supported[1] }
                    : one;
            ss.setEnabledCipherSuites(two);
            got = ss.getEnabledCipherSuites();
            if (got.length != two.length) {
                throw new RuntimeException("Unexpected length: "
                        + Arrays.toString(got));
            }
            for (String s : two) {
                if (!Arrays.asList(got).contains(s)) {
                    throw new RuntimeException("Missing suite " + s);
                }
            }

            String[] last = new String[] { supported[supported.length - 1] };
            ss.setEnabledCipherSuites(last);
            got = ss.getEnabledCipherSuites();
            if (!Arrays.equals(last, got)) {
                throw new RuntimeException("Last suite not reflected: "
                        + Arrays.toString(got));
            }

            if (!Arrays.equals(supportedBefore, ss.getSupportedCipherSuites())) {
                throw new RuntimeException("Supported suites changed by setter");
            }
        } finally {
            ss.close();
        }
    }
}
