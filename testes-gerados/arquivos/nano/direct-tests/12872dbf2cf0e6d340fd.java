/* @test    @summary Test visitUse method of Textifier class    @run main TextifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitUseWithValidString();
        testVisitUseWithEmptyString();
        testVisitUseWithNull();
    }

    private static void testVisitUseWithValidString() {
        Textifier t = new Textifier();
        String use = "Ljava/lang/Object;";
        try {
            t.visitUse(use);
            String output = t.text.get(t.text.size() - 1);
            if (!output.contains("uses") || !output.contains(use)) {
                throw new AssertionError("Output does not contain expected 'uses' or descriptor") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitUseWithValidString", e);
        }
    }

    private static void testVisitUseWithEmptyString() {
        Textifier t = new Textifier();
        String use = "";
        try {
            t.visitUse(use);
            String output = t.text.get(t.text.size() - 1);
            if (!output.contains("uses") || !output.contains(use)) {
                throw new AssertionError("Output does not contain expected 'uses' or empty descriptor") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitUseWithEmptyString", e);
        }
    }

    private static void testVisitUseWithNull() {
        Textifier t = new Textifier();
        try {
            t.visitUse(null);
            String output = t.text.get(t.text.size() - 1);
            if (!output.contains("uses") || !output.contains("null")) {
                throw new AssertionError("Output does not contain expected 'uses' or 'null'") ;
            }
        } catch (NullPointerException e) {
            // Expected if null is not handled
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testVisitUseWithNull", e);
        }
    }
}
