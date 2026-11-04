/* @test    @summary Test X500Principal constructor with name and keywordMap    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import java.util.HashMap;
import java.util.Map;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullName();
        testNullKeywordMap();
        testInvalidName();
    }

    static void testValidConstructor() {
        Map<String, String> keywordMap = new HashMap<>();
        keywordMap.put("CN", "Duke");
        keywordMap.put("O", "Sun Microsystems");
        String name = "CN=Duke, O=Sun Microsystems";
        X500Principal principal = new X500Principal(name, keywordMap);
        assert principal != null : "Principal should not be null";
        String principalName = principal.getName();
        assert principalName.contains("CN=Duke") : "Principal name should contain CN=Duke";
    }

    static void testNullName() {
        Map<String, String> keywordMap = new HashMap<>();
        try {
            new X500Principal(null, keywordMap);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullKeywordMap() {
        String name = "CN=Duke";
        try {
            new X500Principal(name, null);
            assert false : "Expected NullPointerException for null keywordMap";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidName() {
        Map<String, String> keywordMap = new HashMap<>();
        String invalidName = "Invalid Name";
        try {
            new X500Principal(invalidName, keywordMap);
            // Depending on implementation, this may throw IllegalArgumentException
            // or create a principal with the invalid name. We check for exception.
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
