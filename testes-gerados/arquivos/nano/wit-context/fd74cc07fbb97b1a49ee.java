/*
 * @test
 * @summary Test for Textifier.visitRequire method behavior.
 * @run main TextifierWitupTest
 */

import java.io.StringWriter;
import java.io.PrintWriter;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitRequireNormal();
        testVisitRequireNegativeAccess();
    }

    public static void testVisitRequireNormal() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        String require = "java.base";
        int access = 0x0020; // arbitrary access flags
        String version = "11";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        // Temporarily redirect text list to our PrintWriter
        textifier.text.clear();
        textifier.text.add = (String s) -> { pw.println(s); };
        textifier.visitRequire(require, access, version);
        pw.flush();
        String output = sw.toString();
        boolean containsRequire = output.contains("requires java.base;");
        boolean containsVersion = output.contains("// version 11");
        if (!containsRequire || !containsVersion) {
            throw new AssertionError("Normal visitRequire did not produce expected output. Output: " + output);
        }
    }

    public static void testVisitRequireNegativeAccess() {
        jdk.internal.org.objectweb.asm.util.Textifier textifier = new jdk.internal.org.objectweb.asm.util.Textifier();
        String require = "java.base";
        int access = 0x0000; // no special flags
        String version = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        textifier.text.clear();
        textifier.text.add = (String s) -> { pw.println(s); };
        textifier.visitRequire(require, access, version);
        pw.flush();
        String output = sw.toString();
        boolean containsRequire = output.contains("requires java.base;");
        boolean containsVersion = output.contains("// version");
        if (!containsRequire) {
            throw new AssertionError("visitRequire output missing require line. Output: " + output);
        }
        if (containsVersion) {
            throw new AssertionError("visitRequire should not include version line when version is null. Output: " + output);
        }
    }
}
