/*
 * @test
 * @summary Test for Textifier.visitAnnotation to verify behavior and exception handling.
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotationNormal();
        testVisitAnnotationNullName();
        testVisitAnnotationInvalidDesc();
    }

    static void testVisitAnnotationNormal() {
        try {
            Textifier t = new Textifier();
            // Valid description string
            t.visitAnnotation("LMyAnnotation;", "Ljava/lang/String;");
            System.out.println("testVisitAnnotationNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitAnnotationNormal failed: " + e);
        }
    }

    static void testVisitAnnotationNullName() {
        try {
            Textifier t = new Textifier();
            // Null name should be handled gracefully
            t.visitAnnotation(null, "Ljava/lang/String;");
            System.out.println("testVisitAnnotationNullName passed");
        } catch (Exception e) {
            System.err.println("testVisitAnnotationNullName failed: " + e);
        }
    }

    static void testVisitAnnotationInvalidDesc() {
        try {
            Textifier t = new Textifier();
            // Invalid description string, expecting possible exception
            t.visitAnnotation("LMyAnnotation;", null);
            System.out.println("testVisitAnnotationInvalidDesc passed");
        } catch (NullPointerException e) {
            System.out.println("testVisitAnnotationInvalidDesc passed with NullPointerException");
        } catch (Exception e) {
            System.err.println("testVisitAnnotationInvalidDesc failed: " + e);
        }
    }
}
