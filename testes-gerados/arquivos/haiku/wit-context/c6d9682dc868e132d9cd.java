/*
 * @test
 * @summary Verifies the ASMifier.visitLineNumber code generation output
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testPositiveLineIsGenerated();
        testNegativeLineIsGenerated();
        testConsecutiveCallsDoNotDuplicateOutput();
    }

    static void testPositiveLineIsGenerated() {
        ASMifier v = new ASMifier();
        v.visitLineNumber(42, new Label());
        String out = dump(v);
        check(out.contains("cw.visitLineNumber(42, "), "missing generated call for line 42: " + out);
        check(out.contains(");"), "generated call not terminated: " + out);
    }

    static void testNegativeLineIsGenerated() {
        ASMifier v = new ASMifier();
        v.visitLineNumber(-1, new Label());
        String out = dump(v);
        check(out.contains("cw.visitLineNumber(-1, "), "missing generated call for line -1: " + out);
    }

    static void testConsecutiveCallsDoNotDuplicateOutput() {
        ASMifier v = new ASMifier();
        Label label = new Label();
        v.visitLineNumber(1, label);
        v.visitLineNumber(2, label);
        String out = dump(v);
        check(out.contains("cw.visitLineNumber(1, "), "missing line 1: " + out);
        check(out.contains("cw.visitLineNumber(2, "), "missing line 2: " + out);
        check(count(out, "visitLineNumber(") == 2, "expected exactly 2 calls but output was: " + out);
    }

    static String dump(ASMifier v) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        v.print(pw);
        pw.flush();
        return sw.toString();
    }

    static int count(String s, String sub) {
        int n = 0;
        int idx = 0;
        while ((idx = s.indexOf(sub, idx)) >= 0) {
            n++;
            idx += sub.length();
        }
        return n;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
