/*
 * @test
 * @summary Verify Textifier.visitIincInsn renders IINC instructions through the public print API
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String printOf(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void testPositiveOperands() {
        Textifier t = new Textifier();
        t.visitIincInsn(3, 5);
        String out = printOf(t);
        check(out.contains("IINC 3 5"), "expected 'IINC 3 5' in output: " + out);
    }

    static void testNegativeOperands() {
        Textifier t = new Textifier();
        t.visitIincInsn(-1, -2);
        String out = printOf(t);
        check(out.contains("IINC -1 -2"), "expected 'IINC -1 -2' in output: " + out);
    }

    static void testZeroOperands() {
        Textifier t = new Textifier();
        t.visitIincInsn(0, 0);
        String out = printOf(t);
        check(out.contains("IINC 0 0"), "expected 'IINC 0 0' in output: " + out);
    }

    static void testMultipleCallsKeepOrderAndNoStaleBuffer() {
        Textifier t = new Textifier();
        t.visitIincInsn(1, 10);
        t.visitIincInsn(2, 20);
        String out = printOf(t);
        int first = out.indexOf("IINC 1 10");
        int second = out.indexOf("IINC 2 20");
        check(first >= 0, "missing first IINC in output: " + out);
        check(second >= 0, "missing second IINC in output: " + out);
        check(first < second, "IINC instructions out of order: " + out);
        check(out.indexOf("IINC 1 10", first + 1) < 0, "first IINC duplicated: " + out);
    }

    public static void main(String[] args) {
        testPositiveOperands();
        testNegativeOperands();
        testZeroOperands();
        testMultipleCallsKeepOrderAndNoStaleBuffer();
    }
}
