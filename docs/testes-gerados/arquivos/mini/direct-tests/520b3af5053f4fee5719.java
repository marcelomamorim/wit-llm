/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitParameter method
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with non-null name and zero access
        textifier.visitParameter("paramName", 0);
        List<String> text = textifier.getText();
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("paramName")) {
            throw new AssertionError("Expected parameter name 'paramName' in output");
        }
        if (!lastLine.contains("parameter")) {
            throw new AssertionError("Expected 'parameter' keyword in output");
        }

        // Test with null name and non-zero access
        textifier.visitParameter(null, 1);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("<no name>")) {
            throw new AssertionError("Expected '<no name>' placeholder in output when name is null");
        }
        if (!lastLine.contains("parameter")) {
            throw new AssertionError("Expected 'parameter' keyword in output");
        }

        // Test with negative access flags
        textifier.visitParameter("negAccess", -1);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("negAccess")) {
            throw new AssertionError("Expected parameter name 'negAccess' in output");
        }
        if (!lastLine.contains("parameter")) {
            throw new AssertionError("Expected 'parameter' keyword in output");
        }

        System.out.println("All tests passed.");
    }
}
