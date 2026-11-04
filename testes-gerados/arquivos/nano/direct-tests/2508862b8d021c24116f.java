/* @test    @summary Test encoding of PolicyMappingsExtension.    @run main PolicyMappingsExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import sun.security.x509.*;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithEmptyMaps();
        testEncodeWithSampleMaps();
    }

    static void testEncodeWithNullExtensionValue() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.extensionValue = null; // force null to test encode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        // Expect the encoded output to contain the super.encode call with extensionId set
        if (result.length == 0) {
            throw new AssertionError("Encoding with null extensionValue produced empty output");
        }
    }

    static void testEncodeWithEmptyMaps() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.maps = Collections.emptyList();
        ext.encodeThis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result.length == 0) {
            throw new AssertionError("Encoding with empty maps produced empty output");
        }
    }

    static void testEncodeWithSampleMaps() throws IOException {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        // Create dummy CertificatePolicyMap objects with encode method
        CertificatePolicyMap map1 = new CertificatePolicyMap("1.2.3.4", "1.2.3.5") {
            @Override
            public void encode(DerOutputStream out) throws IOException {
                out.writeOID(new ObjectIdentifier("1.2.3.4"));
                out.writeOID(new ObjectIdentifier("1.2.3.5"));
            }
        };
        maps.add(map1);
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result.length == 0) {
            throw new AssertionError("Encoding with sample maps produced empty output");
        }
    }
}
