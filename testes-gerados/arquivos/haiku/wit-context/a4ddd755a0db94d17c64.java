/*
 * @test
 * @summary Witup generated test for OCSPResponse constructor failure on empty input
 * @modules java.base/sun.security.provider.certpath
 * @run main OCSPResponseWitupTest
 */

import sun.security.provider.certpath.OCSPResponse;

public class OCSPResponseWitupTest {

    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            new OCSPResponse(new byte[0]);
        } catch (Exception e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected failure parsing empty OCSP response");
        }
    }
}
