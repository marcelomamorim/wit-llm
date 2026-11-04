/*
 * @test
 * @summary Tests for ASMifier.visitAnnotation method.
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationWithValidInputs();
        testVisitAnnotationWithNullDesc();
        testVisitAnnotationWithEmptyDesc();
    }

    private static void testVisitAnnotationWithValidInputs() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation("LMyAnnotation;", true);
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid inputs");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    private static void testVisitAnnotationWithNullDesc() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation(null, false);
            if (result == null) {
                throw new AssertionError("Expected non-null result for null desc");
            }
        } catch (NullPointerException e) {
            // Expected if null is not handled
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null desc", e);
        }
    }

    private static void testVisitAnnotationWithEmptyDesc() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitAnnotation("", false);
            if (result == null) {
                throw new AssertionError("Expected non-null result for empty desc");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty desc", e);
        }
    }
}
