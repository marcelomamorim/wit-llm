/*
 * @test
 * @summary Test deterministic behavior of ASMifier.visitMethodEnd()
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;
import java.util.List;
import java.util.ArrayList;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        asmifier.visitMethodEnd();
        // The visitMethodEnd method appends a specific string to the text list
        // We verify that the last element of text ends with ".visitEnd();\n"
        List<String> text = getTextList(asmifier);
        if (text.isEmpty()) {
            throw new AssertionError("text list should not be empty after visitMethodEnd");
        }
        String last = text.get(text.size() - 1);
        if (!last.endsWith(".visitEnd();\n")) {
            throw new AssertionError("Last text element does not end with expected string: " + last);
        }
    }

    // Helper method to access the protected field 'text' from ASMifier
    // Since 'text' is protected in Printer superclass, and ASMifier extends Printer,
    // and no public getter is provided, we use a subclass to expose it.
    private static List<String> getTextList(ASMifier asmifier) {
        class ASMifierAccessor extends ASMifier {
            ASMifierAccessor() {
                super();
            }
            List<String> getText() {
                return text;
            }
        }
        ASMifierAccessor accessor = new ASMifierAccessor();
        // Copy the internal state from asmifier to accessor is not possible,
        // so we call visitMethodEnd on accessor instead.
        accessor.visitMethodEnd();
        return accessor.getText();
    }
}
