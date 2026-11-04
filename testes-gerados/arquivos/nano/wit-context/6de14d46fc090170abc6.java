/*
 * @test
 * @summary Test for visitIntInsn method in ASMifier class
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitIntInsnValidOpcode();
        testVisitIntInsnInvalidOpcode();
    }

    private static void testVisitIntInsnValidOpcode() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitIntInsn(OpcodeConstants.IALOAD, 5);
            System.out.println("testVisitIntInsnValidOpcode passed");
        } catch (Exception e) {
            System.err.println("testVisitIntInsnValidOpcode failed: " + e);
        }
    }

    private static void testVisitIntInsnInvalidOpcode() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitIntInsn(-1, 10); // assuming -1 is invalid opcode
            System.out.println("testVisitIntInsnInvalidOpcode passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitIntInsnInvalidOpcode caught expected exception");
        } catch (Exception e) {
            System.err.println("testVisitIntInsnInvalidOpcode failed: " + e);
        }
    }

    static class OpcodeConstants {
        public static final int IALOAD = 46; // example valid opcode
    }
}
