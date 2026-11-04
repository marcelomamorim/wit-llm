/*
 * @test
 * @summary Test for Textifier.visitAnnotation method to verify behavior with various inputs.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationWithValidDescriptor();
        testVisitAnnotationWithEmptyDescriptor();
        testVisitAnnotationWithNullDescriptor();
        testVisitAnnotationWithInvalidDescriptor();
    }

    private static void testVisitAnnotationWithValidDescriptor() {
        try {
            Textifier t = new Textifier();
            Textifier result = t.visitAnnotation("Ljava/lang/Deprecated;", true);
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid descriptor");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid descriptor", e);
        }
    }

    private static void testVisitAnnotationWithEmptyDescriptor() {
        try {
            Textifier t = new Textifier();
            Textifier result = t.visitAnnotation("", false);
            if (result == null) {
                throw new AssertionError("Expected non-null result for empty descriptor");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty descriptor", e);
        }
    }

    private static void testVisitAnnotationWithNullDescriptor() {
        try {
            Textifier t = new Textifier();
            t.visitAnnotation(null, true);
            throw new AssertionError("Expected NullPointerException for null descriptor");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null descriptor", e);
        }
    }

    private static void testVisitAnnotationWithInvalidDescriptor() {
        try {
            Textifier t = new Textifier();
            t.visitAnnotation("InvalidDescriptor", false);
            // Depending on implementation, may or may not throw; assume it does not
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid descriptor", e);
        }
    }
}
