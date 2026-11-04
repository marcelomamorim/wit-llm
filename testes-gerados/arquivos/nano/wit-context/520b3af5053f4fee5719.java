/*
 * @test
 * @summary Test for Textifier.visitParameter method to observe behavior and exception handling.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitParameterWithValidInputs();
        testVisitParameterWithNullName();
        testVisitParameterWithInvalidAccess();
    }

    private static void testVisitParameterWithValidInputs() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitParameter("paramName", 0);
            System.out.println("testVisitParameterWithValidInputs passed.");
        } catch (Exception e) {
            System.err.println("testVisitParameterWithValidInputs failed: " + e);
        }
    }

    private static void testVisitParameterWithNullName() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitParameter(null, 0);
            System.out.println("testVisitParameterWithNullName passed.");
        } catch (Exception e) {
            System.err.println("testVisitParameterWithNullName failed: " + e);
        }
    }

    private static void testVisitParameterWithInvalidAccess() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitParameter("paramName", -1); // invalid access, but method does not throw
            System.out.println("testVisitParameterWithInvalidAccess passed.");
        } catch (Exception e) {
            System.err.println("testVisitParameterWithInvalidAccess failed: " + e);
        }
    }
}
