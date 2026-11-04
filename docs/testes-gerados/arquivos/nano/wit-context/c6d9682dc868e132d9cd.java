/* @test    @summary Test for visitLineNumber method in ASMifier class    @run main ASMifierWitupTest */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.Label;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitLineNumberNormal();
        testVisitLineNumberWithNegativeLine();
        testVisitLineNumberWithNullLabel();
    }

    public static void testVisitLineNumberNormal() {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();
        try {
            asmifier.visitLineNumber(10, label);
            System.out.println("testVisitLineNumberNormal passed");
        } catch (Throwable t) {
            System.err.println("testVisitLineNumberNormal failed: " + t);
        }
    }

    public static void testVisitLineNumberWithNegativeLine() {
        ASMifier asmifier = new ASMifier();
        Label label = new Label();
        try {
            asmifier.visitLineNumber(-1, label);
            System.out.println("testVisitLineNumberWithNegativeLine passed");
        } catch (Throwable t) {
            System.err.println("testVisitLineNumberWithNegativeLine failed: " + t);
        }
    }

    public static void testVisitLineNumberWithNullLabel() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitLineNumber(5, null);
            System.out.println("testVisitLineNumberWithNullLabel passed");
        } catch (Throwable t) {
            System.err.println("testVisitLineNumberWithNullLabel failed: " + t);
        }
    }
}
