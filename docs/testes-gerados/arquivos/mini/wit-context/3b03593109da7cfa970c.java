/*
 * @test
 * @summary Test visitAnnotation method of Textifier for basic behavior and input handling
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationWithNullName();
        testVisitAnnotationWithNonNullName();
    }

    private static void testVisitAnnotationWithNullName() {
        Textifier textifier = new Textifier();
        Textifier result = textifier.visitAnnotation(null, "Ljava/lang/String;");
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null when name is null");
        }
    }

    private static void testVisitAnnotationWithNonNullName() {
        Textifier textifier = new Textifier();
        Textifier result = textifier.visitAnnotation("testName", "Ljava/lang/String;");
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null when name is non-null");
        }
    }
}
