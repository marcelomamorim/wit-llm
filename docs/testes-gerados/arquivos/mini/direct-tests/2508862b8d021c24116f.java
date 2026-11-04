/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.encode(OutputStream) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithEmptyMaps();
        testEncodeWithNonEmptyMaps();
    }

    private static void testEncodeWithEmptyMaps() throws IOException {
        // Create PolicyMappingsExtension with empty list
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty for empty maps");
        }
    }

    private static void testEncodeWithNonEmptyMaps() throws IOException {
        // Create a dummy CertificatePolicyMap list with one element
        List<CertificatePolicyMap> maps = new ArrayList<>();
        maps.add(new DummyCertificatePolicyMap());
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty for non-empty maps");
        }
    }

    // Minimal dummy implementation of CertificatePolicyMap to allow encoding
    private static class DummyCertificatePolicyMap extends CertificatePolicyMap {
        public DummyCertificatePolicyMap() {
            super();
        }

        @Override
        public void encode(DerOutputStream out) throws IOException {
            // Encode a minimal valid DER sequence for testing
            DerOutputStream tmp = new DerOutputStream();
            tmp.putOID(new ObjectIdentifier("1.2.3.4"));
            tmp.putOID(new ObjectIdentifier("1.2.3.5"));
            out.write(DerValue.tag_Sequence, tmp);
        }
    }
}
