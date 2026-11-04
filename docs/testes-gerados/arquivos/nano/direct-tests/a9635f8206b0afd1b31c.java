/* @test    @summary Test ProcessBuilder.from(File) static method    @run main ProcessBuilderWitupTest */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testFromNull();
        testFromValidFile();
    }

    static void testFromNull() {
        try {
            java.lang.ProcessBuilder.Redirect result = java.lang.ProcessBuilder.from(null);
            // Expecting NullPointerException, so if we reach here, test fails
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testFromValidFile() {
        File testFile = new File("testfile.txt");
        java.lang.ProcessBuilder.Redirect result = java.lang.ProcessBuilder.from(testFile);
        if (result == null) {
            throw new AssertionError("Result should not be null for valid file");
        }
        if (result.type() != java.lang.ProcessBuilder.Redirect.Type.READ) {
            throw new AssertionError("Type should be READ");
        }
        if (result.file() != testFile) {
            throw new AssertionError("File returned does not match input file");
        }
        String toStringOutput = result.toString();
        if (!toStringOutput.contains("redirect to read from file \"" + testFile + "\"")) {
            throw new AssertionError("toString() output mismatch: " + toStringOutput);
        }
    }
}
