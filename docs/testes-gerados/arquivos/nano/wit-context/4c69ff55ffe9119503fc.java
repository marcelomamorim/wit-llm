/* @test    @summary Test for PolicyParser.PrincipalEntry constructor handling null inputs    @run main PolicyParserWitupTest */

import sun.security.provider.PolicyParser;

public class PolicyParserWitupTest {

    public static void main(String[] args) {
        testPrincipalEntryNullPrincipalClass();
        testPrincipalEntryNullPrincipalName();
    }

    private static void testPrincipalEntryNullPrincipalClass() {
        try {
            new PolicyParser.PrincipalEntry(null, "ValidName");
            System.out.println("FAIL: Expected NullPointerException for null principalClass");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null principalClass");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testPrincipalEntryNullPrincipalName() {
        try {
            new PolicyParser.PrincipalEntry("ValidClass", null);
            System.out.println("FAIL: Expected NullPointerException for null principalName");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null principalName");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
