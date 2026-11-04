/*
 * @test
 * @summary Test sun.security.provider.PolicyParser.PrincipalEntry constructor behavior
 * @run main PolicyParserWitupTest
 */

public class PolicyParserWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullPrincipalClass();
        testNullPrincipalName();
    }

    private static void testValidConstructor() {
        String cls = "java.security.Principal";
        String name = "TestPrincipal";
        sun.security.provider.PolicyParser.PrincipalEntry pe = new sun.security.provider.PolicyParser.PrincipalEntry(cls, name);
        if (!cls.equals(pe.principalClass)) {
            throw new RuntimeException("principalClass not set correctly");
        }
        if (!name.equals(pe.principalName)) {
            throw new RuntimeException("principalName not set correctly");
        }
    }

    private static void testNullPrincipalClass() {
        try {
            new sun.security.provider.PolicyParser.PrincipalEntry(null, "name");
            throw new RuntimeException("Expected NullPointerException for null principalClass");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null.principalClass.or.principalName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testNullPrincipalName() {
        try {
            new sun.security.provider.PolicyParser.PrincipalEntry("class", null);
            throw new RuntimeException("Expected NullPointerException for null principalName");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null.principalClass.or.principalName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
