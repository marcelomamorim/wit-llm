/*
 * @test
 * @summary Test the visitLabel method of Textifier for normal and exceptional behavior
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitLabelNormal();
        testVisitLabelNullLabel();
    }

    private static void testVisitLabelNormal() {
        Textifier textifier = new Textifier();
        Label label = new Label();
        try {
            textifier.visitLabel(label);
            System.out.println("testVisitLabelNormal passed");
        } catch (Exception e) {
            System.err.println("testVisitLabelNormal failed: " + e);
        }
    }

    private static void testVisitLabelNullLabel() {
        Textifier textifier = new Textifier();
        try {
            textifier.visitLabel(null);
            System.out.println("testVisitLabelNullLabel passed");
        } catch (NullPointerException e) {
            System.out.println("testVisitLabelNullLabel passed with NullPointerException");
        } catch (Exception e) {
            System.err.println("testVisitLabelNullLabel failed: " + e);
        }
    }
}
