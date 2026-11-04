/*
 * @test
 * @summary Constructor behavior of SNIHostName for valid, null, empty and trailing-dot hostnames
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;

public class SNIHostNameWitupTest {

    public static void main(String[] args) throws Exception {
        new SNIHostName("example.com");
        new SNIHostName("b\u00fccher.example");

        expectNullPointerException();
        expectIllegalArgumentException("");
        expectIllegalArgumentException("example.com.");
    }

    static void expectNullPointerException() {
        try {
            new SNIHostName((String) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null hostname");
    }

    static void expectIllegalArgumentException(String hostname) {
        try {
            new SNIHostName(hostname);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for: " + hostname);
    }
}
