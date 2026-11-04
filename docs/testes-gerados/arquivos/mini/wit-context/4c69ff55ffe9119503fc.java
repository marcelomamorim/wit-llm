/*
 * @test
 * @summary Test sun.security.provider.PolicyParser.PrincipalEntry constructor for null checks and normal behavior
 * @run main PolicyParserWitupTest
 */

import sun.security.provider.PolicyParser;
import sun.security.util.LocalizedMessage;

public class PolicyParserWitupTest {
    public static void main(String[] args) {
        testConstructorRejectsNullPrincipalClass();
        testConstructorRejectsNullPrincipalName();
        testConstructorAcceptsValidArguments();
    }

    private static void testConstructorRejectsNullPrincipalClass() {
        try {
            new PolicyParser.PrincipalEntry(null, "validName");
            throw new RuntimeException("Expected NullPointerException for null principalClass");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null.principalClass.or.principalName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testConstructorRejectsNullPrincipalName() {
        try {
            new PolicyParser.PrincipalEntry("validClass", null);
            throw new RuntimeException("Expected NullPointerException for null principalName");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null.principalClass.or.principalName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testConstructorAcceptsValidArguments() {
        PolicyParser.PrincipalEntry entry = new PolicyParser.PrincipalEntry("validClass", "validName");
        if (!"validClass".equals(entry.principalClass)) {
            throw new RuntimeException("principalClass not set correctly");
        }
        if (!"validName".equals(entry.principalName)) {
            throw new RuntimeException("principalName not set correctly");
        }
    }
}
