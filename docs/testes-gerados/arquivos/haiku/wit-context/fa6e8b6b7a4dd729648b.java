/*
 * @test
 * @summary Checks ASMifier.visitPackage output for simple package names and repeated calls
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier a = new ASMifier();
        a.visitPackage("java/lang");
        String out = render(a);
        check(out.contains("mdv.visitPackage(\"java/lang\");"), out);

        ASMifier b = new ASMifier();
        b.visitPackage("a/b");
        b.visitPackage("c/d");
        String out2 = render(b);
        check(out2.contains("mdv.visitPackage(\"a/b\");"), out2);
        check(out2.contains("mdv.visitPackage(\"c/d\");"), out2);
    }

    static String render(ASMifier v) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        v.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean condition, String output) {
        if (!condition) {
            throw new RuntimeException("Unexpected output: " + output);
        }
    }
}
