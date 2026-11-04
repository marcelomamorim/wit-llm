/*
 * @test
 * @summary Verifies that Textifier.visitMultiANewArrayInsn emits a MULTIANEWARRAY line with the descriptor and dimension count
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) throws Exception {
        testTwoDimensionalIntArray();
        testObjectArrayWithThreeDims();
        testConsecutiveCallsAreBothRendered();
    }

    static void testTwoDimensionalIntArray() {
        Textifier t = new Textifier();
        t.visitMultiANewArrayInsn("[[I", 2);
        String out = render(t);
        check(out.contains("MULTIANEWARRAY [[I 2"), "unexpected output: " + out);
    }

    static void testObjectArrayWithThreeDims() {
        Textifier t = new Textifier();
        t.visitMultiANewArrayInsn("[Ljava/lang/String;", 3);
        String out = render(t);
        check(out.contains("MULTIANEWARRAY [Ljava/lang/String; 3"), "unexpected output: " + out);
    }

    static void testConsecutiveCallsAreBothRendered() {
        Textifier t = new Textifier();
        t.visitMultiANewArrayInsn("[I", 1);
        t.visitMultiANewArrayInsn("[[J", 2);
        String out = render(t);
        check(out.contains("MULTIANEWARRAY [I 1"), "missing first insn: " + out);
        check(out.contains("MULTIANEWARRAY [[J 2"), "missing second insn: " + out);
    }

    static String render(Textifier t) {
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
