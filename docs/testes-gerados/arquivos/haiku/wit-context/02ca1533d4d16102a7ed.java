/*
 * @test
 * @summary Tests X500Principal.getName(String, Map) null and format validation and basic observable output
 * @run main X500PrincipalWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        X500Principal p = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        Map<String, String> empty = new HashMap<>();

        expectNPE(p, X500Principal.RFC2253, null);
        expectNPE(p, null, null);
        expectNPE(p, X500Principal.RFC1779, null);

        expectIAE(p, null, empty);
        expectIAE(p, "UNKNOWN", empty);
        expectIAE(p, "", empty);

        String rfc2253 = p.getName(X500Principal.RFC2253, empty);
        if (rfc2253 == null || !rfc2253.contains("Duke")) {
            throw new RuntimeException("unexpected RFC2253 output: " + rfc2253);
        }

        String rfc1779 = p.getName(X500Principal.RFC1779, empty);
        if (rfc1779 == null || !rfc1779.contains("Duke")) {
            throw new RuntimeException("unexpected RFC1779 output: " + rfc1779);
        }

        String lower = p.getName("rfc2253", Collections.<String, String>emptyMap());
        if (lower == null || !lower.contains("Duke")) {
            throw new RuntimeException("unexpected case-insensitive output: " + lower);
        }
    }

    private static void expectNPE(X500Principal p, String format, Map<String, String> map) {
        try {
            p.getName(format, map);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for format=" + format);
    }

    private static void expectIAE(X500Principal p, String format, Map<String, String> map) {
        try {
            p.getName(format, map);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for format=" + format);
    }
}
