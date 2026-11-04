/*
 * @test
 * @summary Tests for ASMifier.visitMaxs method behavior and exception paths
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMaxsNormal();
        testVisitMaxsNegativeMaxStack();
        testVisitMaxsNegativeMaxLocals();
    }

    public static void testVisitMaxsNormal() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMaxs(10, 20);
            System.out.println("testVisitMaxsNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsNormal failed: " + e);
        }
    }

    public static void testVisitMaxsNegativeMaxStack() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMaxs(-1, 20);
            System.err.println("testVisitMaxsNegativeMaxStack failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitMaxsNegativeMaxStack passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsNegativeMaxStack failed: " + e);
        }
    }

    public static void testVisitMaxsNegativeMaxLocals() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitMaxs(10, -5);
            System.err.println("testVisitMaxsNegativeMaxLocals failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitMaxsNegativeMaxLocals passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsNegativeMaxLocals failed: " + e);
        }
    }
}
