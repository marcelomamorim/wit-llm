/*
 * @test
 * @summary Checks public observable output of Textifier.visitLdcInsn for String, Integer, null and sequential calls
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        String s = render("abc");
        if (!s.contains("LDC \"abc\"")) {
            throw new RuntimeException("String LDC not rendered: " + s);
        }

        String i = render(Integer.valueOf(42));
        if (!i.contains("LDC 42")) {
            throw new RuntimeException("Integer LDC not rendered: " + i);
        }

        String n = render(null);
        if (!n.contains("LDC null")) {
            throw new RuntimeException("null LDC not rendered: " + n);
        }

        Textifier t = new Textifier();
        t.visitLdcInsn("x");
        t.visitLdcInsn(Integer.valueOf(7));
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        String out = sw.toString();
        if (!out.contains("LDC \"x\"") || !out.contains("LDC 7")) {
            throw new RuntimeException("Sequential LDC entries missing: " + out);
        }
        if (out.contains("\"x\"7")) {
            throw new RuntimeException("Buffer not reset between calls: " + out);
        }
    }

    static String render(Object cst) {
        Textifier t = new Textifier();
        t.visitLdcInsn(cst);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }
}
