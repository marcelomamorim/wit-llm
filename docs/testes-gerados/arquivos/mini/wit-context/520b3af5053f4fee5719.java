/*
 * @test
 * @summary Test Textifier.visitParameter method for basic functionality and null name handling
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with normal parameter name and access
        textifier.visitParameter("paramName", 1);
        List<String> text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitParameter call");
        }
        String line = text.get(text.size() - 1);
        if (!line.contains("paramName")) {
            throw new AssertionError("Output should contain the parameter name");
        }
        if (!line.contains("// parameter ")) {
            throw new AssertionError("Output should contain the parameter comment prefix");
        }

        // Test with null parameter name
        textifier.visitParameter(null, 0);
        text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitParameter call with null name");
        }
        line = text.get(text.size() - 1);
        if (!line.contains("<no name>")) {
            throw new AssertionError("Output should contain '<no name>' when parameter name is null");
        }

        // Test with various access flags
        textifier.visitParameter("testAccess", 0);
        textifier.visitParameter("testAccess", 0x10); // synthetic flag
        textifier.visitParameter("testAccess", 0x20); // mandated flag

        // Just verify no exceptions and output is generated
        text = textifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after multiple visitParameter calls");
        }

        System.out.println("All tests passed.");
    }
}
