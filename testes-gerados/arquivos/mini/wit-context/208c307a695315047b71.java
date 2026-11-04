/*
 * @test
 * @summary Tests for jdk.internal.module.Checks.requireQualifiedClassName
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireQualifiedClassNameNull();
        testRequireQualifiedClassNameNoDot();
        testRequireQualifiedClassNameValid();
    }

    private static void testRequireQualifiedClassNameNull() {
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", null);
            throw new Error("Expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
            if (!"Null package name".equals(e.getMessage()) && !e.getMessage().contains("null")) {
                // Accept any message containing null or similar
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testRequireQualifiedClassNameNoDot() {
        String name = "SimpleName";
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", name);
            throw new Error("Expected IllegalArgumentException for unqualified name");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains(name) || !e.getMessage().contains("qualified name")) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testRequireQualifiedClassNameValid() {
        String validName = "java.lang.String";
        String result = jdk.internal.module.Checks.requireQualifiedClassName("test", validName);
        if (!validName.equals(result)) {
            throw new Error("Expected returned name to be same as input");
        }
    }

}
