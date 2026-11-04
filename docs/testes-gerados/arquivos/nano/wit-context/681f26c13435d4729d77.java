/*
 * @test
 * @summary Tests for visitMultiANewArrayInsn method in ASMifier class
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMultiANewArrayInsnValid();
        testVisitMultiANewArrayInsnInvalidDesc();
        testVisitMultiANewArrayInsnInvalidDims();
    }

    public static void testVisitMultiANewArrayInsnValid() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMultiANewArrayInsn("[I", 2);
            System.out.println("testVisitMultiANewArrayInsnValid passed");
        } catch (Exception e) {
            System.err.println("testVisitMultiANewArrayInsnValid failed: " + e);
        }
    }

    public static void testVisitMultiANewArrayInsnInvalidDesc() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMultiANewArrayInsn(null, 1);
            System.err.println("testVisitMultiANewArrayInsnInvalidDesc failed: no exception");
        } catch (NullPointerException e) {
            System.out.println("testVisitMultiANewArrayInsnInvalidDesc passed");
        } catch (Exception e) {
            System.err.println("testVisitMultiANewArrayInsnInvalidDesc failed: " + e);
        }
    }

    public static void testVisitMultiANewArrayInsnInvalidDims() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMultiANewArrayInsn("[I", -1);
            System.err.println("testVisitMultiANewArrayInsnInvalidDims failed: no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitMultiANewArrayInsnInvalidDims passed");
        } catch (Exception e) {
            System.err.println("testVisitMultiANewArrayInsnInvalidDims failed: " + e);
        }
    }
}
