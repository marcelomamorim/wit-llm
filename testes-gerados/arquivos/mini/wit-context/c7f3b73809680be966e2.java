/*
 * @test
 * @summary Test Textifier.visitMainClass(String) for basic functionality and regression
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with a normal class name string
        String className = "com/example/Main";
        textifier.visitMainClass(className);
        String output = textifier.getText().get(textifier.getText().size() - 1);
        if (!output.contains(className)) {
            throw new AssertionError("Output does not contain the main class name: " + output);
        }

        // Test with empty string
        textifier.visitMainClass("");
        output = textifier.getText().get(textifier.getText().size() - 1);
        if (!output.contains("  // main class ")) {
            throw new AssertionError("Output missing main class comment for empty string: " + output);
        }

        // Test with null - according to source code, no explicit null check, so expect NullPointerException
        boolean npeThrown = false;
        try {
            textifier.visitMainClass(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to visitMainClass");
        }

        System.out.println("Textifier.visitMainClass tests passed.");
    }
}
