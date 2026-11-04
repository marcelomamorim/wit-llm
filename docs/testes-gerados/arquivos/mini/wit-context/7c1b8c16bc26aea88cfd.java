/*
 * @test
 * @summary Test visitMainClass method of ASMifier for basic functionality and input handling
 * @run main ASMifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal class name string
        asmifier.visitMainClass("java/lang/Object");
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitMainClass call");
        }
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("mdv.visitMainClass(")) {
            throw new AssertionError("Output line should contain 'mdv.visitMainClass('");
        }
        if (!lastLine.contains("java/lang/Object")) {
            throw new AssertionError("Output line should contain the main class name");
        }

        // Test with empty string
        asmifier.visitMainClass("");
        text = asmifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("mdv.visitMainClass(")) {
            throw new AssertionError("Output line should contain 'mdv.visitMainClass(' for empty string");
        }

        // Test with null input - according to source code, no explicit null check, so expect NPE or handled
        boolean npeThrown = false;
        try {
            asmifier.visitMainClass(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            // If no exception, ensure output contains 'null' string
            text = asmifier.getText();
            lastLine = text.get(text.size() - 1);
            if (!lastLine.contains("null")) {
                throw new AssertionError("Output line should contain 'null' when null is passed");
            }
        }
    }
}
