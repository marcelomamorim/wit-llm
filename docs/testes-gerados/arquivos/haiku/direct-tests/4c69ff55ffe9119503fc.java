/*
 * @test
 * @summary Verifies PolicyParser.PrincipalEntry constructor accepts non-null arguments and rejects null arguments with NullPointerException
 * @modules java.base/sun.security.provider
 * @run main PolicyParserWitupTest
 */

import sun.security.provider.PolicyParser.PrincipalEntry;

public class PolicyParserWitupTest {

    public static void main(String[] args) {
        PrincipalEntry entry = new PrincipalEntry("javax.security.auth.x500.X500Principal", "CN=Duke");
        if (entry == null) {
            throw new RuntimeException("constructor returned null");
        }
        expectNpe(null, "CN=Duke");
        expectNpe("javax.security.auth.x500.X500Principal", null);
        expectNpe(null, null);
    }

    static void expectNpe(String principalClass, String principalName) {
        try {
            new PrincipalEntry(principalClass, principalName);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for class="
                + principalClass + ", name=" + principalName);
    }
}
