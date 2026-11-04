/*
 * @test
 * @summary Verifies Textifier.visitSource output for null and non-null file and debug arguments
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testFileAndDebug();
        testNullArguments();
        testFileOnly();
        testMultipleCalls();
    }

    private static String render(jdk.internal.org.objectweb.asm.util.Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    private static void testFileAndDebug() {
        jdk.internal.org.objectweb.asm.util.Textifier t = new jdk.internal.org.objectweb.asm.util.Textifier();
        t.visitSource("Foo.java", "dbg");
        String out = render(t);
        check(out.contains("// compiled from: Foo.java"), "missing file comment: " + out);
        check(out.contains("// debug info: dbg"), "missing debug comment: " + out);
    }

    private static void testNullArguments() {
        jdk.internal.org.objectweb.asm.util.Textifier t = new jdk.internal.org.objectweb.asm.util.Textifier();
        t.visitSource(null, null);
        String out = render(t);
        check(!out.contains("//"), "unexpected output for null arguments: " + out);
    }

    private static void testFileOnly() {
        jdk.internal.org.objectweb.asm.util.Textifier t = new jdk.internal.org.objectweb.asm.util.Textifier();
        t.visitSource("A.java", null);
        String out = render(t);
        check(out.contains("// compiled from: A.java"), "missing file comment: " + out);
        check(!out.contains("// debug info:"), "unexpected debug comment: " + out);
    }

    private static void testMultipleCalls() {
        jdk.internal.org.objectweb.asm.util.Textifier t = new jdk.internal.org.objectweb.asm.util.Textifier();
        t.visitSource("First.java", null);
        t.visitSource(null, "second-debug");
        String out = render(t);
        check(out.contains("// compiled from: First.java"), "missing first entry: " + out);
        check(out.contains("// debug info: second-debug"), "missing second entry: " + out);
    }
}
