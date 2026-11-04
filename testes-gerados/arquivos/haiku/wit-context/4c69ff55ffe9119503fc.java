/*
 * @test
 * @summary Verifies that PolicyParser.PrincipalEntry constructor rejects null principalClass or principalName and accepts non-null values
 * @modules java.base/sun.security.provider
 * @run main PolicyParserWitupTest
 */

import sun.security.provider.PolicyParser;

public class PolicyParserWitupTest {

    public static void main(String[] args) {
        expectNullPointerException(null, "principalName");
        expectNullPointerException("principalClass", null);
        expectNullPointerException(null, null);
        PolicyParser.PrincipalEntry entry =
            new PolicyParser.PrincipalEntry("com.example.Principal", "duke");
        if (entry == null) {
            throw new RuntimeException("Constructor returned null for valid arguments");
        }
    }

    private static void expectNullPointerException(String principalClass,
                                                   String principalName) {
        try {
            new PolicyParser.PrincipalEntry(principalClass, principalName);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for principalClass="
            + principalClass + ", principalName=" + principalName);
    }
}
