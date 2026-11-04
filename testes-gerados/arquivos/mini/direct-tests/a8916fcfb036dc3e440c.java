/*
 * @test
 * @summary Test ASMifier.visitOpen method behavior with various inputs
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null modules
        asmifier.visitOpen("java.base", 0, (String[]) null);
        String output1 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output1.contains("mdv.visitOpen(")) {
            throw new AssertionError("Output should contain 'mdv.visitOpen('");
        }
        if (!output1.contains("java.base")) {
            throw new AssertionError("Output should contain the package name 'java.base'");
        }

        // Test with empty modules array
        asmifier.visitOpen("java.base", 1, new String[0]);
        String output2 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output2.contains("mdv.visitOpen(")) {
            throw new AssertionError("Output should contain 'mdv.visitOpen('");
        }
        if (!output2.contains("java.base")) {
            throw new AssertionError("Output should contain the package name 'java.base'");
        }
        if (output2.contains("new String[]")) {
            throw new AssertionError("Output should not contain modules array when empty");
        }

        // Test with one module
        asmifier.visitOpen("java.base", 2, "mod1");
        String output3 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output3.contains("mdv.visitOpen(")) {
            throw new AssertionError("Output should contain 'mdv.visitOpen('");
        }
        if (!output3.contains("java.base")) {
            throw new AssertionError("Output should contain the package name 'java.base'");
        }
        if (!output3.contains("new String[]")) {
            throw new AssertionError("Output should contain modules array when non-empty");
        }
        if (!output3.contains("mod1")) {
            throw new AssertionError("Output should contain the module name 'mod1'");
        }

        // Test with multiple modules
        asmifier.visitOpen("java.base", 4, "mod1", "mod2", "mod3");
        String output4 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output4.contains("mdv.visitOpen(")) {
            throw new AssertionError("Output should contain 'mdv.visitOpen('");
        }
        if (!output4.contains("java.base")) {
            throw new AssertionError("Output should contain the package name 'java.base'");
        }
        if (!output4.contains("new String[]")) {
            throw new AssertionError("Output should contain modules array when non-empty");
        }
        if (!output4.contains("mod1") || !output4.contains("mod2") || !output4.contains("mod3")) {
            throw new AssertionError("Output should contain all module names 'mod1', 'mod2', 'mod3'");
        }

        System.out.println("All ASMifier.visitOpen tests passed.");
    }
}
