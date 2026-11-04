/*
 * @test
 * @summary Test sun.security.provider.certpath.OCSPResponse.getSingleExtensions() returns unmodifiable map
 * @run main OCSPResponseWitupTest
 */

import java.util.Map;
import java.security.cert.Extension;
import sun.security.provider.certpath.OCSPResponse;

public class OCSPResponseWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an OCSPResponse with empty byte array to test getSingleExtensions
        OCSPResponse resp = new OCSPResponse(new byte[0]);
        Map<String, Extension> exts = resp.getSingleExtensions();
        if (exts == null) {
            throw new RuntimeException("getSingleExtensions() returned null");
        }
        // The returned map should be unmodifiable, test that by attempting to add an entry
        try {
            exts.put("test", null);
            throw new RuntimeException("Returned map is modifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
