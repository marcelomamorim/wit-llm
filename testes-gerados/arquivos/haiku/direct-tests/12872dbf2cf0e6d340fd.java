/*
 * @test
 * @summary Verify that Textifier.visitUse emits a "uses" line for the given internal name
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testSingleUse();
        testUsesKeepOrder();
    }

    static void testSingleUse() {
        Textifier t = new Textifier();
        t.visitUse("java/lang/Object");
        String out = dump(t);
        if (!out.contains("uses java/lang/Object;")) {
            throw new RuntimeException("Missing uses line in output: " + out);
        }
    }

    static void testUsesKeepOrder() {
        Textifier t = new Textifier();
        t.visitUse("a/A");
        t.visitUse("b/B");
        String out = dump(t);
        int ia = out.indexOf("uses a/A;");
        int ib = out.indexOf("uses b/B;");
        if (ia < 0 || ib < 0 || ia > ib) {
            throw new RuntimeException("Unexpected uses order or missing lines: " + out);
        }
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }
}
