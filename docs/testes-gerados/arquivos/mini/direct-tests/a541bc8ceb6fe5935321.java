/*
 * @test
 * @summary Test ASMifier.visitSource method with various inputs
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with both file and debug as null
        asmifier.visitSource(null, null);
        String output1 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output1.equals("cw.visitSource(null, null);\n\n")) {
            throw new AssertionError("Failed test with null, null: " + output1);
        }

        // Test with file as non-null and debug as null
        asmifier.visitSource("TestFile.java", null);
        String output2 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output2.equals("cw.visitSource(\"TestFile.java\", null);\n\n")) {
            throw new AssertionError("Failed test with file non-null, debug null: " + output2);
        }

        // Test with file as null and debug as non-null
        asmifier.visitSource(null, "DebugInfo");
        String output3 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output3.equals("cw.visitSource(null, \"DebugInfo\");\n\n")) {
            throw new AssertionError("Failed test with file null, debug non-null: " + output3);
        }

        // Test with both file and debug as non-null
        asmifier.visitSource("File.java", "DebugInfo");
        String output4 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output4.equals("cw.visitSource(\"File.java\", \"DebugInfo\");\n\n")) {
            throw new AssertionError("Failed test with file and debug non-null: " + output4);
        }
    }
}
