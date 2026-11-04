/*
 * @test
 * @summary Test requireQualifiedClassName method in Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testValidQualifiedName();
        testNullName();
        testUnqualifiedName();
    }

    private static void testValidQualifiedName() {
        String validName = "java.lang.String";
        String result = jdk.internal.module.Checks.requireQualifiedClassName("test", validName);
        if (!result.equals(validName)) {
            throw new AssertionError("Expected returned name to be '" + validName + "' but was '" + result + "'");
        }
    }

    private static void testNullName() {
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", null);
            throw new AssertionError("Expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testUnqualifiedName() {
        String unqualified = "String";
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", unqualified);
            throw new AssertionError("Expected IllegalArgumentException for unqualified name");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("is not a qualified name")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
    }
}
