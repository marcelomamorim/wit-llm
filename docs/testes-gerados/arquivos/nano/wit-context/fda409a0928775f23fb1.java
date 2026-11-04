/* @test    @summary Test for AnnotationVisitor constructor with valid and invalid API values    @run main AnnotationVisitorWitupTest */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import static java.lang.System.out;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        testValidApi();
        testInvalidApiBelow();
        testInvalidApiAbove();
    }

    private static void testValidApi() {
        try {
            AnnotationVisitor av = new AnnotationVisitor(4, null);
            out.println("Valid API 4: PASSED");
        } catch (Exception e) {
            out.println("Valid API 4: FAILED - " + e);
        }
        try {
            AnnotationVisitor av = new AnnotationVisitor(5, null);
            out.println("Valid API 5: PASSED");
        } catch (Exception e) {
            out.println("Valid API 5: FAILED - " + e);
        }
        try {
            AnnotationVisitor av = new AnnotationVisitor(6, null);
            out.println("Valid API 6: PASSED");
        } catch (Exception e) {
            out.println("Valid API 6: FAILED - " + e);
        }
    }

    private static void testInvalidApiBelow() {
        try {
            AnnotationVisitor av = new AnnotationVisitor(3, null);
            out.println("Invalid API 3: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            out.println("Invalid API 3: PASSED");
        } catch (Exception e) {
            out.println("Invalid API 3: FAILED - wrong exception: " + e);
        }
    }

    private static void testInvalidApiAbove() {
        try {
            AnnotationVisitor av = new AnnotationVisitor(7, null);
            out.println("Invalid API 7: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            out.println("Invalid API 7: PASSED");
        } catch (Exception e) {
            out.println("Invalid API 7: FAILED - wrong exception: " + e);
        }
    }
}
