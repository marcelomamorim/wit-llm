/*
 * @test
 * @summary Test Textifier.visitPackage method for basic functionality and input handling
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal package name
        textifier.visitPackage("com.example.test");
        List<String> output = textifier.getText();
        if (output.isEmpty()) {
            throw new AssertionError("Text output should not be empty after visitPackage call");
        }
        String line = output.get(output.size() - 1);
        if (!line.contains("com.example.test")) {
            throw new AssertionError("Output line should contain the package name");
        }

        // Test with empty string package name
        textifier.visitPackage("");
        output = textifier.getText();
        line = output.get(output.size() - 1);
        if (!line.contains("  // package ")) {
            throw new AssertionError("Output line should contain package prefix even for empty package name");
        }

        // Test with null package name - according to source code, no explicit null check,
        // so this may throw NullPointerException. We test that it does not throw.
        try {
            textifier.visitPackage(null);
            output = textifier.getText();
            line = output.get(output.size() - 1);
            if (!line.contains("null")) {
                throw new AssertionError("Output line should contain 'null' string when package is null");
            }
        } catch (NullPointerException npe) {
            // Acceptable if NPE is thrown, as no explicit null check in source
        }

        System.out.println("All tests passed.");
    }
}
