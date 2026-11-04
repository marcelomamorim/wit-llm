/*
 * @test
 * @summary Test visitAnnotation method of Textifier for basic functionality and exception safety
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationBasic();
    }

    private static void testVisitAnnotationBasic() {
        Textifier textifier = new Textifier();

        // Test with normal descriptor and visible true
        Textifier result1 = textifier.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (result1 == null) {
            throw new AssertionError("visitAnnotation returned null for visible=true");
        }

        // Test with normal descriptor and visible false
        Textifier result2 = textifier.visitAnnotation("Ljava/lang/Override;", false);
        if (result2 == null) {
            throw new AssertionError("visitAnnotation returned null for visible=false");
        }

        // Test with empty descriptor string
        Textifier result3 = textifier.visitAnnotation("", true);
        if (result3 == null) {
            throw new AssertionError("visitAnnotation returned null for empty descriptor");
        }

        // Test with null descriptor - according to source code, no explicit null check,
        // so this may throw NullPointerException or cause failure internally.
        // We test that it does not throw IllegalArgumentException or other unexpected exceptions.
        try {
            Textifier result4 = textifier.visitAnnotation(null, true);
            if (result4 == null) {
                throw new AssertionError("visitAnnotation returned null for null descriptor");
            }
        } catch (NullPointerException npe) {
            // Acceptable behavior since no explicit null check in source
        } catch (Exception e) {
            throw new AssertionError("visitAnnotation threw unexpected exception for null descriptor: " + e);
        }
    }
}
