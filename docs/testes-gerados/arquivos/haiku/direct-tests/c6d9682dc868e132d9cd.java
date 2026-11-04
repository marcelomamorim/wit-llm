/*
 * @test
 * @summary Checks ASMifier.visitLineNumber output for line numbers and labels
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testSingleLineNumber();
        testNegativeLineNumber();
        testSameLabelReusesName();
        testDifferentLabelsDifferentNames();
    }

    static void testSingleLineNumber() {
        ASMifier a = new ASMifier();
        Label l = new Label();
        a.visitLineNumber(42, l);
        String out = dump(a);
        check(out.contains(".visitLineNumber(42, "), "missing line number: " + out);
        check(out.endsWith(");\n"), "unexpected terminator: " + out);
    }

    static void testNegativeLineNumber() {
        ASMifier a = new ASMifier();
        Label l = new Label();
        a.visitLineNumber(-1, l);
        String out = dump(a);
        check(out.contains(".visitLineNumber(-1, "), "missing negative line: " + out);
    }

    static void testSameLabelReusesName() {
        ASMifier a = new ASMifier();
        Label l = new Label();
        a.visitLineNumber(1, l);
        a.visitLineNumber(2, l);
        String out = dump(a);
        String[] lines = out.split("\n");
        check(lines.length == 2, "expected 2 lines: " + out);
        check(labelOf(lines[0]).equals(labelOf(lines[1])),
              "same label should have same name: " + out);
    }

    static void testDifferentLabelsDifferentNames() {
        ASMifier a = new ASMifier();
        Label l1 = new Label();
        Label l2 = new Label();
        a.visitLineNumber(1, l1);
        a.visitLineNumber(2, l2);
        String out = dump(a);
        String[] lines = out.split("\n");
        check(lines.length == 2, "expected 2 lines: " + out);
        check(!labelOf(lines[0]).equals(labelOf(lines[1])),
              "different labels should have different names: " + out);
    }

    static String labelOf(String line) {
        int s = line.indexOf(", ") + 2;
        int e = line.lastIndexOf(");");
        return line.substring(s, e);
    }

    static String dump(ASMifier a) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        a.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
