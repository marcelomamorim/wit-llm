/*
 * @test
 * @summary Tests for ASMifier.visitJumpInsn method behavior.
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitJumpInsnValidOpcode();
        testVisitJumpInsnInvalidOpcode();
    }

    private static void testVisitJumpInsnValidOpcode() {
        try {
            ASMifier asmifier = new ASMifier();
            Label label = new Label();
            asmifier.visitJumpInsn(0, label); // 0 is a valid opcode in OPCODES array
            System.out.println("testVisitJumpInsnValidOpcode passed");
        } catch (Exception e) {
            System.err.println("testVisitJumpInsnValidOpcode failed: " + e);
        }
    }

    private static void testVisitJumpInsnInvalidOpcode() {
        try {
            ASMifier asmifier = new ASMifier();
            Label label = new Label();
            asmifier.visitJumpInsn(-1, label); // invalid opcode, should throw ArrayIndexOutOfBoundsException
            System.err.println("testVisitJumpInsnInvalidOpcode failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testVisitJumpInsnInvalidOpcode passed");
        } catch (Exception e) {
            System.err.println("testVisitJumpInsnInvalidOpcode failed: " + e);
        }
    }
}
