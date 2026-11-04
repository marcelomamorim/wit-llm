/*
 * @test
 * @summary Tests the public X500Principal(String, Map) constructor: null checks, invalid names and valid names
 * @run main X500PrincipalWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullName() {
        boolean thrown = false;
        try {
            new X500Principal(null, new HashMap<String, String>());
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null name must throw NullPointerException");
    }

    static void testNullKeywordMap() {
        boolean thrown = false;
        try {
            new X500Principal("CN=Duke", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null keywordMap must throw NullPointerException");
    }

    static void testImproperName() {
        boolean thrown = false;
        boolean hasCause = false;
        try {
            new X500Principal("CN", new HashMap<String, String>());
        } catch (IllegalArgumentException e) {
            thrown = true;
            hasCause = e.getCause() != null;
        }
        check(thrown, "improper name must throw IllegalArgumentException");
        check(hasCause, "IllegalArgumentException must carry the original cause");
    }

    static void testValidNameRfc2253() {
        Map<String, String> map = new HashMap<>();
        X500Principal p = new X500Principal("CN=Duke", map);
        check("CN=Duke".equals(p.getName(X500Principal.RFC2253)),
              "unexpected RFC2253 name: " + p.getName(X500Principal.RFC2253));
    }

    static void testValidNameEmptyMapAccepted() {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US",
                                            new HashMap<String, String>());
        check(p.getName(X500Principal.RFC2253).contains("CN=Duke"),
              "parsed principal should contain CN=Duke");
    }

    public static void main(String[] args) {
        testNullName();
        testNullKeywordMap();
        testImproperName();
        testValidNameRfc2253();
        testValidNameEmptyMapAccepted();
    }
}
