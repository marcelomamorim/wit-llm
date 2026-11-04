/*
 * @test    @summary Test visitParameter method of Textifier class
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitParameterWithNameAndAccess();
        testVisitParameterWithNullName();
        testVisitParameterWithDifferentAccess();
    }

    static void testVisitParameterWithNameAndAccess() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        List<String> output = new ArrayList<>();
        // Override text list to capture output
        textifier.text = output;
        String paramName = "param1";
        int access = 0x0001; // public
        textifier.visitParameter(paramName, access);
        String expected = "\t// parameter \u0001 param1\n";
        if (!output.contains(expected)) {
            throw new AssertionError("Expected output to contain: " + expected + " but was: " + output);
        }
    }

    static void testVisitParameterWithNullName() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        List<String> output = new ArrayList<>();
        textifier.text = output;
        String paramName = null;
        int access = 0x0002; // private
        textifier.visitParameter(paramName, access);
        String expected = "\t// parameter \u0002 \u003cno name\n";
        if (!output.contains(expected)) {
            throw new AssertionError("Expected output to contain: " + expected + " but was: " + output);
        }
    }

    static void testVisitParameterWithDifferentAccess() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        List<String> output = new ArrayList<>();
        textifier.text = output;
        String paramName = "param2";
        int access = 0x0004; // protected
        textifier.visitParameter(paramName, access);
        String expected = "\t// parameter \u0004 param2\n";
        if (!output.contains(expected)) {
            throw new AssertionError("Expected output to contain: " + expected + " but was: " + output);
        }
    }
}
