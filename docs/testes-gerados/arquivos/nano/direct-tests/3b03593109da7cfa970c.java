/* @test    @summary Test visitAnnotation method of Textifier class    @run main TextifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationWithNullName();
        testVisitAnnotationWithEmptyDescriptor();
        testVisitAnnotationWithValidInputs();
    }

    private static void testVisitAnnotationWithNullName() {
        Textifier t = new Textifier();
        try {
            Textifier result = t.visitAnnotation(null, "Ljava/lang/String;");
            if (result == null) {
                System.err.println("Failed: visitAnnotation with null name returned null");
            } else {
                System.out.println("Passed: visitAnnotation with null name");
            }
        } catch (Exception e) {
            System.err.println("Exception in testVisitAnnotationWithNullName: " + e);
        }
    }

    private static void testVisitAnnotationWithEmptyDescriptor() {
        Textifier t = new Textifier();
        try {
            Textifier result = t.visitAnnotation("testName", "");
            if (result == null) {
                System.err.println("Failed: visitAnnotation with empty descriptor returned null");
            } else {
                System.out.println("Passed: visitAnnotation with empty descriptor");
            }
        } catch (Exception e) {
            System.err.println("Exception in testVisitAnnotationWithEmptyDescriptor: " + e);
        }
    }

    private static void testVisitAnnotationWithValidInputs() {
        Textifier t = new Textifier();
        try {
            Textifier result = t.visitAnnotation("myAnnotation", "Ljava/lang/Integer;");
            if (result == null) {
                System.err.println("Failed: visitAnnotation with valid inputs returned null");
            } else {
                System.out.println("Passed: visitAnnotation with valid inputs");
            }
        } catch (Exception e) {
            System.err.println("Exception in testVisitAnnotationWithValidInputs: " + e);
        }
    }
}
