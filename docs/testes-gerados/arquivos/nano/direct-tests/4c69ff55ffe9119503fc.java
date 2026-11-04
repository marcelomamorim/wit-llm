/* @test    @summary Test PrincipalEntry constructor with valid inputs    @run main PrincipalEntryConstructorTest */

import sun.security.provider.PolicyParser;

public class PrincipalEntryConstructorTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullPrincipalClass();
        testNullPrincipalName();
    }

    static void testValidInputs() {
        String className = "com.example.MyPrincipal";
        String name = "PrincipalName";
        PolicyParser.PrincipalEntry entry = new PolicyParser.PrincipalEntry(className, name);
        assert entry != null : "PrincipalEntry should not be null for valid inputs";
    }

    static void testNullPrincipalClass() {
        String nullClass = null;
        String name = "PrincipalName";
        try {
            new PolicyParser.PrincipalEntry(nullClass, name);
            assert false : "Expected NullPointerException for null principalClass";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testNullPrincipalName() {
        String className = "com.example.MyPrincipal";
        String nullName = null;
        try {
            new PolicyParser.PrincipalEntry(className, nullName);
            assert false : "Expected NullPointerException for null principalName";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
