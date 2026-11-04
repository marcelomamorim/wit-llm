/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.ASMifier.visitOpen method behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with null modules
        asmifier.visitOpen("mypackage", 0, (String[]) null);
        List<String> text1 = asmifier.getText();
        if (text1.isEmpty()) {
            throw new Error("visitOpen did not add output with null modules");
        }
        String output1 = text1.get(text1.size() - 1);
        if (!output1.contains("mypackage")) {
            throw new Error("visitOpen output missing package name");
        }
        if (!output1.contains("mdv.visitOpen")) {
            throw new Error("visitOpen output missing method call");
        }

        // Test with empty modules array
        asmifier.visitOpen("pkg", 1);
        List<String> text2 = asmifier.getText();
        if (text2.isEmpty()) {
            throw new Error("visitOpen did not add output with empty modules");
        }
        String output2 = text2.get(text2.size() - 1);
        if (!output2.contains("pkg")) {
            throw new Error("visitOpen output missing package name for empty modules");
        }
        if (output2.contains("new String[]")) {
            throw new Error("visitOpen output should not contain modules array for empty modules");
        }

        // Test with non-empty modules array
        String[] modules = new String[] {"mod1", "mod2"};
        asmifier.visitOpen("pkg2", 2, modules);
        List<String> text3 = asmifier.getText();
        if (text3.isEmpty()) {
            throw new Error("visitOpen did not add output with non-empty modules");
        }
        String output3 = text3.get(text3.size() - 1);
        if (!output3.contains("pkg2")) {
            throw new Error("visitOpen output missing package name for non-empty modules");
        }
        if (!output3.contains("new String[]")) {
            throw new Error("visitOpen output missing modules array for non-empty modules");
        }
        if (!output3.contains("mod1") || !output3.contains("mod2")) {
            throw new Error("visitOpen output missing module names");
        }

        System.out.println("All ASMifier.visitOpen tests passed.");
    }
}
