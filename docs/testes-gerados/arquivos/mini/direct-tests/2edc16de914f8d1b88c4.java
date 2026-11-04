/*
 * @test
 * @summary Test X500Principal(String, Map) constructor behavior
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        testNullName();
        testNullKeywordMap();
        testValidNameAndEmptyKeywordMap();
        testInvalidNameThrowsIAE();
    }

    private static void testNullName() {
        try {
            new X500Principal(null, new HashMap<String, String>());
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullKeywordMap() {
        try {
            new X500Principal("CN=Test", null);
            throw new RuntimeException("Expected NullPointerException for null keywordMap");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidNameAndEmptyKeywordMap() {
        Map<String, String> emptyMap = new HashMap<String, String>();
        X500Principal p = new X500Principal("CN=Test", emptyMap);
        String name = p.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Expected non-empty name from X500Principal");
        }
    }

    private static void testInvalidNameThrowsIAE() {
        Map<String, String> emptyMap = new HashMap<String, String>();
        try {
            new X500Principal("Invalid=Name=With=Bad=Format", emptyMap);
            throw new RuntimeException("Expected IllegalArgumentException for invalid name format");
        } catch (IllegalArgumentException iae) {
            if (iae.getCause() == null) {
                throw new RuntimeException("IllegalArgumentException cause should not be null");
            }
            // expected
        }
    }
}
