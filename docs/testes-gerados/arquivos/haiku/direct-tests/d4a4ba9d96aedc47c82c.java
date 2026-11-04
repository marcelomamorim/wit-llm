/*
 * @test
 * @summary Checks that Textifier.visitTypeInsn appends the opcode name and internal type name to the printed text
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String render(Textifier t) {
        StringWriter sw = new StringWriter();
        t.print(new PrintWriter(sw));
        return sw.toString();
    }

    static void testNewInsn() {
        Textifier t = new Textifier();
        t.visitTypeInsn(Opcodes.NEW, "java/lang/String");
        String out = render(t);
        check(out.contains("NEW java/lang/String"), "NEW line missing: " + out);
    }

    static void testCheckCastInsn() {
        Textifier t = new Textifier();
        t.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
        String out = render(t);
        check(out.contains("CHECKCAST java/lang/Integer"), "CHECKCAST line missing: " + out);
    }

    static void testSequenceOrder() {
        Textifier t = new Textifier();
        t.visitTypeInsn(Opcodes.INSTANCEOF, "java/lang/Runnable");
        t.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
        String out = render(t);
        int i = out.indexOf("INSTANCEOF java/lang/Runnable");
        int j = out.indexOf("ANEWARRAY java/lang/Object");
        check(i >= 0, "INSTANCEOF line missing: " + out);
        check(j >= 0, "ANEWARRAY line missing: " + out);
        check(i < j, "instructions printed out of order: " + out);
    }

    public static void main(String[] args) {
        testNewInsn();
        testCheckCastInsn();
        testSequenceOrder();
    }
}
