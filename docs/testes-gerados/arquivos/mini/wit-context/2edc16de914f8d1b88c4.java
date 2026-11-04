/*
 * @test
 * @summary Test X500Principal(String, Map) constructor for null arguments and invalid input
 * @run main javax.security.auth.x500.X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrows();
        testNullKeywordMapThrows();
        testInvalidNameThrowsIAE();
        testValidNameAndKeywordMap();
    }

    private static void testNullNameThrows() {
        try {
            new X500Principal(null, new HashMap<String, String>());
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullKeywordMapThrows() {
        try {
            new X500Principal("CN=Test", null);
            throw new RuntimeException("Expected NullPointerException for null keywordMap");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidNameThrowsIAE() {
        Map<String, String> emptyMap = new HashMap<String, String>();
        try {
            // invalid name string that should cause X500Name constructor to fail
            new X500Principal("Invalid@@Name", emptyMap);
            throw new RuntimeException("Expected IllegalArgumentException for invalid name");
        } catch (IllegalArgumentException e) {
            // expected
            if (e.getCause() == null) {
                throw new RuntimeException("IllegalArgumentException cause should not be null");
            }
        }
    }

    private static void testValidNameAndKeywordMap() {
        Map<String, String> keywordMap = new HashMap<String, String>();
        keywordMap.put("CN", "2.5.4.3");
        X500Principal p = new X500Principal("CN=Valid Name", keywordMap);
        String name = p.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Expected non-empty name from valid X500Principal");
        }
    }
}
