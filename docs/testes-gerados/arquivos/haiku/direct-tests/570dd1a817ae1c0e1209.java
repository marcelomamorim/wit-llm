/*
 * @test
 * @summary Verify PolicyMappingsExtension.get(String) returns the map list for the MAP name (case-insensitive) and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructorReturnsEmptyList();
        testCaseInsensitiveMapName();
        testListConstructorReturnsSameList();
        testUnknownNameThrowsIOException();
        testEmptyNameThrowsIOException();
    }

    static void testDefaultConstructorReturnsEmptyList() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        List<CertificatePolicyMap> got = ext.get(PolicyMappingsExtension.MAP);
        check(got != null, "default get(MAP) returned null");
        check(got.isEmpty(), "default get(MAP) should be empty");
    }

    static void testCaseInsensitiveMapName() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        List<CertificatePolicyMap> upper = ext.get("MAP");
        List<CertificatePolicyMap> mixed = ext.get("Map");
        check(upper != null && upper.isEmpty(), "get(\"MAP\") failed");
        check(mixed != null && mixed.isEmpty(), "get(\"Map\") failed");
    }

    static void testListConstructorReturnsSameList() throws IOException {
        List<CertificatePolicyMap> src = new ArrayList<CertificatePolicyMap>();
        PolicyMappingsExtension ext = new PolicyMappingsExtension(src);
        List<CertificatePolicyMap> got = ext.get(PolicyMappingsExtension.MAP);
        check(got == src, "get(MAP) should return the list passed to the constructor");
    }

    static void testUnknownNameThrowsIOException() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.get("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(\"unknown\") should throw IOException");
    }

    static void testEmptyNameThrowsIOException() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.get("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(\"\") should throw IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
