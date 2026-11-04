/*
 * @test
 * @summary Verifies ASMifier.visitJumpInsn output and invalid opcode handling
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) throws Exception {
        testValidJumpEmitsCode();
        testInvalidOpcodeThrows();
    }

    static void testValidJumpEmitsCode() {
        ASMifier asm = new ASMifier();
        Label label = new Label();
        asm.visitJumpInsn(Opcodes.IFEQ, label);
        String out = render(asm);
        if (!out.contains("visitJumpInsn(IFEQ, ")) {
            throw new AssertionError("Missing jump instruction in output: " + out);
        }
    }

    static void testInvalidOpcodeThrows() {
        ASMifier asm = new ASMifier();
        Label label = new Label();
        try {
            asm.visitJumpInsn(-1, label);
        } catch (ArrayIndexOutOfBoundsException expected) {
            return;
        }
        throw new AssertionError("Expected ArrayIndexOutOfBoundsException for opcode -1");
    }

    static String render(ASMifier asm) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asm.print(pw);
        pw.flush();
        return sw.toString();
    }
}
