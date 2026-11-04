/*
 * @test
 * @summary Test Textifier.visitLdcInsn behavior with different constant types
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with String constant
        textifier.visitLdcInsn("testString");
        List<String> text = textifier.getText();
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("LDC \"testString\"")) {
            throw new AssertionError("Expected LDC with quoted string, got: " + lastLine);
        }

        // Test with Type constant
        Type type = Type.getType("Ljava/lang/String;");
        textifier.visitLdcInsn(type);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("LDC Ljava/lang/String;.class")) {
            throw new AssertionError("Expected LDC with Type descriptor, got: " + lastLine);
        }

        // Test with Integer constant
        Integer intConst = Integer.valueOf(123);
        textifier.visitLdcInsn(intConst);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("LDC 123")) {
            throw new AssertionError("Expected LDC with integer constant, got: " + lastLine);
        }
    }
}
