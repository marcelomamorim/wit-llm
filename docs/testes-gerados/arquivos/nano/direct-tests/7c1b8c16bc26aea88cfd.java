/* @test    @summary Test ASMifier.visitMainClass method for correct string output    @run main ASMifierWitupTest */

import java.io.StringWriter;
import java.io.PrintWriter;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMainClassWithValidInput();
        testVisitMainClassWithEmptyString();
        testVisitMainClassWithNullInput();
    }

    public static void testVisitMainClassWithValidInput() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        String mainClass = "com/example/MyClass";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        // Redirecting output to buffer
        asmifier.buf = new StringBuilder();
        asmifier.visitMainClass(mainClass);
        String output = asmifier.buf.toString();
        String expected = "mdv.visitMainClass(" + appendConstantString(mainClass) + ");\n";
        if (!output.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + output);
        }
    }

    public static void testVisitMainClassWithEmptyString() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        String mainClass = "";
        asmifier.buf = new StringBuilder();
        asmifier.visitMainClass(mainClass);
        String output = asmifier.buf.toString();
        String expected = "mdv.visitMainClass(" + appendConstantString(mainClass) + ");\n";
        if (!output.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + output);
        }
    }

    public static void testVisitMainClassWithNullInput() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        String mainClass = null;
        try {
            asmifier.buf = new StringBuilder();
            asmifier.visitMainClass(mainClass);
            String output = asmifier.buf.toString();
            String expected = "mdv.visitMainClass(" + appendConstantString(null) + ");\n";
            if (!output.equals(expected)) {
                throw new AssertionError("Expected: " + expected + ", but got: " + output);
            }
        } catch (NullPointerException e) {
            // Expected if appendConstant does not handle null
        }
    }

    private static String appendConstantString(String value) {
        if (value == null) {
            return "null";
        }
        return "\"" + value + "\"";
    }
}
