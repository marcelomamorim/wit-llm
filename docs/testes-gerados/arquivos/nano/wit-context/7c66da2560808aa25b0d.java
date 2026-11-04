/*
 * @test
 * @summary Test the visitIincInsn method of Textifier for normal and edge cases.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitIincInsnNormal();
        testVisitIincInsnNegativeVar();
        testVisitIincInsnLargeIncrement();
    }

    static void testVisitIincInsnNormal() {
        Textifier t = new Textifier();
        try {
            t.visitIincInsn(1, 5);
            System.out.println("testVisitIincInsnNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitIincInsnNormal failed: " + e);
        }
    }

    static void testVisitIincInsnNegativeVar() {
        Textifier t = new Textifier();
        try {
            t.visitIincInsn(-1, 10);
            System.out.println("testVisitIincInsnNegativeVar passed");
        } catch (Exception e) {
            System.err.println("testVisitIincInsnNegativeVar failed: " + e);
        }
    }

    static void testVisitIincInsnLargeIncrement() {
        Textifier t = new Textifier();
        try {
            t.visitIincInsn(2, Integer.MAX_VALUE);
            System.out.println("testVisitIincInsnLargeIncrement passed");
        } catch (Exception e) {
            System.err.println("testVisitIincInsnLargeIncrement failed: " + e);
        }
    }
}
