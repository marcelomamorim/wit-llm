/*
 * @test
 * @summary Verify Textifier.visitIntInsn output for BIPUSH, SIPUSH and NEWARRAY
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testBipushPositive();
        testSipushNegative();
        testNewarrayUsesTypeName();
    }

    static void testBipushPositive() {
        Textifier t = new Textifier();
        t.visitIntInsn(16, 42);
        String out = dump(t);
        check(out.contains("BIPUSH 42"), "BIPUSH operand not printed: " + out);
        check(out.endsWith("\n"), "line must end with newline: " + out);
    }

    static void testSipushNegative() {
        Textifier t = new Textifier();
        t.visitIntInsn(17, -5);
        String out = dump(t);
        check(out.contains("SIPUSH -5"), "SIPUSH negative operand not printed: " + out);
    }

    static void testNewarrayUsesTypeName() {
        Textifier t = new Textifier();
        t.visitIntInsn(188, 10);
        String out = dump(t);
        check(out.contains("NEWARRAY"), "NEWARRAY opcode not printed: " + out);
        check(out.contains("T_INT"), "NEWARRAY type name not printed: " + out);
        check(!out.contains("NEWARRAY 10"), "NEWARRAY should not print raw operand: " + out);
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
