/*
 * @test
 * @summary Test for Textifier.visitMaxs method to verify handling of maxStack and maxLocals parameters.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitMaxsWithValidParameters();
        testVisitMaxsWithZeroParameters();
        testVisitMaxsWithNegativeParameters();
    }

    private static void testVisitMaxsWithValidParameters() {
        Textifier t = new Textifier();
        try {
            t.visitMaxs(10, 5);
            System.out.println("testVisitMaxsWithValidParameters passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsWithValidParameters failed: " + e);
        }
    }

    private static void testVisitMaxsWithZeroParameters() {
        Textifier t = new Textifier();
        try {
            t.visitMaxs(0, 0);
            System.out.println("testVisitMaxsWithZeroParameters passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsWithZeroParameters failed: " + e);
        }
    }

    private static void testVisitMaxsWithNegativeParameters() {
        Textifier t = new Textifier();
        try {
            t.visitMaxs(-1, -1);
            System.out.println("testVisitMaxsWithNegativeParameters passed");
        } catch (Exception e) {
            System.err.println("testVisitMaxsWithNegativeParameters failed: " + e);
        }
    }
}
