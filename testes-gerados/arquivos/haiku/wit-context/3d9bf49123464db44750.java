/*
 * @test
 * @summary Witup-generated tests for IPAddressName(String) constructor validation
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws Exception {
        expectIOException(null);
        expectIOException("");
        expectIOException("10.0.0.1/");
        expectIOException("abc");

        IPAddressName valid = new IPAddressName("10.0.0.1");
        if (valid == null) {
            throw new AssertionError("constructor returned null for a valid IPv4 address");
        }
    }

    static void expectIOException(String name) {
        try {
            new IPAddressName(name);
        } catch (IOException e) {
            return;
        }
        throw new AssertionError("expected IOException for input: " + name);
    }
}
