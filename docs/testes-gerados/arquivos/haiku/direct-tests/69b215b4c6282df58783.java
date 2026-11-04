/*
 * @test
 * @summary Verify ASMifier.visitTypeInsn emits the expected ASM code line for type instructions
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static String printOf(ASMifier asmifier) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asmifier.print(pw);
        pw.flush();
        return sw.toString();
    }

    public static void main(String[] args) {
        testSingleTypeInsnEmitsVisitCall();
        testMultipleTypeInsnsAreAppendedInOrder();
    }

    private static void testSingleTypeInsnEmitsVisitCall() {
        ASMifier asmifier = new ASMifier();
        asmifier.visitTypeInsn(187, "java/lang/String"); // NEW
        String out = printOf(asmifier);
        check(out.contains(".visitTypeInsn("), "missing visitTypeInsn call: " + out);
        check(out.contains("NEW"), "missing NEW opcode name: " + out);
        check(out.contains("java/lang/String"), "missing type name: " + out);
    }

    private static void testMultipleTypeInsnsAreAppendedInOrder() {
        ASMifier asmifier = new ASMifier();
        asmifier.visitTypeInsn(187, "java/lang/Object"); // NEW
        asmifier.visitTypeInsn(192, "java/util/List");   // CHECKCAST
        String out = printOf(asmifier);
        int newIdx = out.indexOf("NEW");
        int castIdx = out.indexOf("CHECKCAST");
        check(newIdx >= 0, "missing NEW: " + out);
        check(castIdx >= 0, "missing CHECKCAST: " + out);
        check(newIdx < castIdx, "instructions not in call order: " + out);
        check(out.contains("java/util/List"), "missing second type: " + out);
    }
}
