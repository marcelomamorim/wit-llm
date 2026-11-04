/*
 * @test
 * @summary Test ASMifier.visitExport method behavior with various inputs
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null modules
        asmifier.visitExport("java.base", 0, (String[]) null);
        String output1 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output1.contains("mdv.visitExport(") || !output1.contains("java.base") || !output1.contains("| 0x8000")) {
            throw new AssertionError("visitExport output incorrect for null modules");
        }

        // Test with empty modules array
        asmifier.visitExport("java.sql", 1, new String[0]);
        String output2 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output2.contains("mdv.visitExport(") || !output2.contains("java.sql") || !output2.contains("| 0x8000")) {
            throw new AssertionError("visitExport output incorrect for empty modules");
        }
        if (output2.contains("new String[]")) {
            throw new AssertionError("visitExport should not include modules array when empty");
        }

        // Test with one module
        asmifier.visitExport("java.xml", 2, "mod1");
        String output3 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output3.contains("mdv.visitExport(") || !output3.contains("java.xml") || !output3.contains("| 0x8000") || !output3.contains("new String[] { ")) {
            throw new AssertionError("visitExport output incorrect for one module");
        }
        if (!output3.contains("\"mod1\"")) {
            throw new AssertionError("visitExport output missing module name");
        }

        // Test with multiple modules
        asmifier.visitExport("java.desktop", 4, "modA", "modB", "modC");
        String output4 = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!output4.contains("mdv.visitExport(") || !output4.contains("java.desktop") || !output4.contains("| 0x8000") || !output4.contains("new String[] { ")) {
            throw new AssertionError("visitExport output incorrect for multiple modules");
        }
        if (!output4.contains("\"modA\"") || !output4.contains("\"modB\"") || !output4.contains("\"modC\"")) {
            throw new AssertionError("visitExport output missing one or more module names");
        }

        System.out.println("All ASMifier.visitExport tests passed.");
    }
}
