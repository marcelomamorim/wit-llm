/*
 * @test
 * @summary Test for visitAnnotationEnd method in ASMifier class.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationEnd();
    }

    public static void testVisitAnnotationEnd() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitAnnotationEnd();
            // Since the method has no return, test passes if no exception is thrown.
            System.out.println("visitAnnotationEnd executed successfully.");
        } catch (Exception e) {
            throw new AssertionError("Exception thrown during visitAnnotationEnd", e);
        }
    }
}
