/*
 * @test
 * @summary Witup-generated checks for URIName public constructor behavior
 * @modules java.base/sun.security.x509
 * @run main URINameWitupTest
 */

import java.io.IOException;
import sun.security.x509.URIName;

public class URINameWitupTest {

    public static void main(String[] args) throws Exception {
        new URIName("http://example.com/path");

        boolean thrown = false;
        try {
            new URIName("http://exa mple.com");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for invalid URI");
        }
    }
}
