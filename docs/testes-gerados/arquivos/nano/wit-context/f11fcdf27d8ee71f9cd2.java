/*
 * @test
 * @summary Test for ASMifier.visitAnnotationDefault() method.
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationDefault();
    }

    public static void testVisitAnnotationDefault() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitAnnotationDefault();
        } catch (IllegalArgumentException e) {
            // Expected exception based on the method's behavior and expaths
            System.out.println("Caught expected IllegalArgumentException: " + e.getMessage());
        } catch (Exception e) {
            // Fail the test if any other exception occurs
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
