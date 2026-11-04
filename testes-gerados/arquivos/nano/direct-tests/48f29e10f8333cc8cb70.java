/* @test    @summary Test visitAnnotation method of Textifier class    @run main TextifierWitupTest */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitAnnotation();
    }

    public static void testVisitAnnotation() {
        Textifier textifier = new Textifier();
        String descriptor = "Ljava/lang/Override;";
        boolean visible = true;
        Textifier result = textifier.visitAnnotation(descriptor, visible);
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null");
        }
        String textOutput = result.getText();
        if (textOutput == null || textOutput.isEmpty()) {
            throw new AssertionError("Resulting text is null or empty");
        }
        if (!textOutput.contains("@")) {
            throw new AssertionError("Output does not contain '@' symbol");
        }
        if (visible && !textOutput.contains(")\n")) {
            throw new AssertionError("Visible annotation should end with ')\n'" );
        }
        if (!visible && !textOutput.contains(") // invisible\n")) {
            throw new AssertionError("Invisible annotation should end with ') // invisible\n'");
        }
        System.out.println("visitAnnotation test passed.");
    }
}
