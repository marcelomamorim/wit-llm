/*
 * @test
 * @summary Test Textifier.visitRequire method behavior with various access flags and version
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.Opcodes;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with no flags and null version
        textifier.visitRequire("java.base", 0, null);
        String output0 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output0.contains("requires java.base;") || output0.contains("transitive") || output0.contains("static") || output0.contains("version")) {
            throw new AssertionError("Failed test with no flags and null version: " + output0);
        }

        // Test with ACC_TRANSITIVE flag
        textifier.visitRequire("java.sql", Opcodes.ACC_TRANSITIVE, null);
        String output1 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output1.contains("transitive ") || !output1.contains("requires java.sql;") || output1.contains("static") || output1.contains("version")) {
            throw new AssertionError("Failed test with ACC_TRANSITIVE flag: " + output1);
        }

        // Test with ACC_STATIC_PHASE flag
        textifier.visitRequire("java.xml", Opcodes.ACC_STATIC_PHASE, null);
        String output2 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output2.contains("static ") || !output2.contains("requires java.xml;") || output2.contains("transitive") || output2.contains("version")) {
            throw new AssertionError("Failed test with ACC_STATIC_PHASE flag: " + output2);
        }

        // Test with both ACC_TRANSITIVE and ACC_STATIC_PHASE flags
        int bothFlags = Opcodes.ACC_TRANSITIVE | Opcodes.ACC_STATIC_PHASE;
        textifier.visitRequire("java.logging", bothFlags, null);
        String output3 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output3.contains("transitive ") || !output3.contains("static ") || !output3.contains("requires java.logging;") || output3.contains("version")) {
            throw new AssertionError("Failed test with both ACC_TRANSITIVE and ACC_STATIC_PHASE flags: " + output3);
        }

        // Test with version string provided
        textifier.visitRequire("java.management", 0, "9");
        String output4 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output4.contains("requires java.management;") || !output4.contains("version 9")) {
            throw new AssertionError("Failed test with version string: " + output4);
        }

        // Test with empty require string and version
        textifier.visitRequire("", 0, "1.0");
        String output5 = textifier.getText().get(textifier.getText().size() - 1);
        if (!output5.contains("requires ;") || !output5.contains("version 1.0")) {
            throw new AssertionError("Failed test with empty require string and version: " + output5);
        }

        System.out.println("All tests passed.");
    }
}
