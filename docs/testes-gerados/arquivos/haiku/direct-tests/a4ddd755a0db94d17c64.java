/*
 * @test
 * @summary getSingleExtensions returns an empty, unmodifiable map for a minimal OCSP response
 * @modules java.base/sun.security.provider.certpath
 * @run main OCSPResponseWitupTest
 */

import java.security.cert.Extension;
import java.util.Map;
import sun.security.provider.certpath.OCSPResponse;

public class OCSPResponseWitupTest {

    public static void main(String[] args) throws Exception {
        // OCSPResponse ::= SEQUENCE { responseStatus ENUMERATED(1 = malformedRequest) }
        byte[] bytes = {0x30, 0x03, 0x0A, 0x01, 0x01};
        OCSPResponse resp = new OCSPResponse(bytes);

        Map<String, Extension> ext = resp.getSingleExtensions();
        if (ext == null) {
            throw new RuntimeException("getSingleExtensions returned null");
        }
        if (!ext.isEmpty()) {
            throw new RuntimeException("Expected empty extensions map, size=" + ext.size());
        }

        boolean threw = false;
        try {
            ext.put("1.2.3", null);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Returned map must be unmodifiable");
        }

        if (resp.getSingleExtensions().size() != 0) {
            throw new RuntimeException("Map changed unexpectedly after failed put");
        }
    }
}
