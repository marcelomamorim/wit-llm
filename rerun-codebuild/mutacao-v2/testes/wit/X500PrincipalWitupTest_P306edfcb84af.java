/*
 * @test
 * @summary Test javax.security.auth.x500.X500Principal.getName(String, Map) for exception and normal cases
 * @run main X500PrincipalWitupTest_P306edfcb84af
 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest_P306edfcb84af {
    public static void main(String[] args) throws Exception {
        testGetNameNullOidMap();
        testGetNameNullFormatNonNullOidMap();
        testGetNameInvalidFormatNonNullOidMap();
        testGetNameValidFormats();
    }

    private static void testGetNameNullOidMap() {
        X500Principal p = new X500Principal("CN=Test");
        try {
            p.getName("RFC2253", null);
            throw new RuntimeException("Expected NullPointerException for null oidMap");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGetNameNullFormatNonNullOidMap() {
        X500Principal p = new X500Principal("CN=Test");
        Map<String, String> map = new HashMap<>();
        try {
            p.getName(null, map);
            throw new RuntimeException("Expected IllegalArgumentException for null format with non-null oidMap");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testGetNameInvalidFormatNonNullOidMap() {
        X500Principal p = new X500Principal("CN=Test");
        Map<String, String> map = new HashMap<>();
        try {
            p.getName("INVALID_FORMAT", map);
            throw new RuntimeException("Expected IllegalArgumentException for invalid format with non-null oidMap");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testGetNameValidFormats() {
        X500Principal p = new X500Principal("CN=Test");
        Map<String, String> map = new HashMap<>();
        // RFC1779 format
        String name1779 = p.getName("RFC1779", map);
        if (name1779 == null || name1779.isEmpty()) {
            throw new RuntimeException("getName returned null or empty for RFC1779 format");
        }
        // RFC2253 format
        String name2253 = p.getName("RFC2253", map);
        if (name2253 == null || name2253.isEmpty()) {
            throw new RuntimeException("getName returned null or empty for RFC2253 format");
        }
    }
}
