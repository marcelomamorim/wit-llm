/*
 * @test
 * @summary Checks observable text produced by Textifier.visitJumpInsn
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testSingleJumpInsn();
        testLabelNamesAreDistinctForDistinctLabels();
        testSameLabelReusesName();
    }

    static void testSingleJumpInsn() {
        Textifier t = new Textifier();
        Label l = new Label();
        t.visitJumpInsn(Opcodes.IFEQ, l);
        String out = dump(t);
        check(out.contains("IFEQ L0"), "expected 'IFEQ L0' in: " + out);
        check(out.endsWith("\n"), "expected line terminator in: " + out);
    }

    static void testLabelNamesAreDistinctForDistinctLabels() {
        Textifier t = new Textifier();
        Label a = new Label();
        Label b = new Label();
        t.visitJumpInsn(Opcodes.IFEQ, a);
        t.visitJumpInsn(Opcodes.IFNE, b);
        String out = dump(t);
        check(out.contains("IFEQ L0"), "missing IFEQ L0 in: " + out);
        check(out.contains("IFNE L1"), "missing IFNE L1 in: " + out);
    }

    static void testSameLabelReusesName() {
        Textifier t = new Textifier();
        Label l = new Label();
        t.visitJumpInsn(Opcodes.GOTO, l);
        t.visitJumpInsn(Opcodes.IFEQ, l);
        String out = dump(t);
        check(out.contains("GOTO L0"), "missing GOTO L0 in: " + out);
        check(out.contains("IFEQ L0"), "missing IFEQ L0 in: " + out);
        check(!out.contains("L1"), "unexpected second label name in: " + out);
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
