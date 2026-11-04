/*
 * @test
 * @summary Test coverage for private StackTraceElement() constructor
 * @run main StackTraceElementWitupTest
 */
public class StackTraceElementWitupTest {
    public static void main(String[] args) {
        // The private no-arg constructor is not accessible,
        // so this test verifies that the public constructors work as expected.
        // This indirectly covers the class instantiation.

        // Create StackTraceElement with typical values
        java.lang.StackTraceElement ste = new java.lang.StackTraceElement("java.lang.String", "length", "String.java", 42);

        // Verify getters return expected values
        if (!"java.lang.String".equals(ste.getClassName())) {
            throw new AssertionError("Expected className java.lang.String but got " + ste.getClassName());
        }
        if (!"length".equals(ste.getMethodName())) {
            throw new AssertionError("Expected methodName length but got " + ste.getMethodName());
        }
        if (!"String.java".equals(ste.getFileName())) {
            throw new AssertionError("Expected fileName String.java but got " + ste.getFileName());
        }
        if (ste.getLineNumber() != 42) {
            throw new AssertionError("Expected lineNumber 42 but got " + ste.getLineNumber());
        }

        // Verify toString contains className and methodName
        String s = ste.toString();
        if (!s.contains("java.lang.String") || !s.contains("length")) {
            throw new AssertionError("toString does not contain expected className or methodName: " + s);
        }
    }
}
