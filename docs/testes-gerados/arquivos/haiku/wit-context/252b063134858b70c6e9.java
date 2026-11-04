/*
 * @test
 * @summary Checks public DNSName(String) validation as a compatibility baseline; DNSName(DerValue) is not directly exercised
 * @modules java.base/sun.security.x509
 * @run main DNSNameWitupTest
 */
import sun.security.x509.DNSName;
import java.io.IOException;

public class DNSNameWitupTest {
    public static void main(String[] args) throws Exception {
        DNSName ok = new DNSName("example.com");
        if (!ok.toString().contains("example.com")) {
            throw new RuntimeException("valid name not preserved: " + ok);
        }
        expectIOException("");
        expectIOException("a b.com");
        expectIOException(".example.com");
        expectIOException("example.com.");
    }

    private static void expectIOException(String name) {
        try {
            new DNSName(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("IOException expected for: '" + name + "'");
    }
}
