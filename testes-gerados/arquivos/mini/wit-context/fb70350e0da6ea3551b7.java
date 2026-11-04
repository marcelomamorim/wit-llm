/*
 * @test
 * @summary Test basic behavior of ASMifier.visitRequire method
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with normal inputs
        asmifier.visitRequire("moduleName", 0, "1.0");
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitRequire call");
        }
        String output = text.get(text.size() - 1);
        if (!output.contains("mdv.visitRequire(")) {
            throw new AssertionError("Output should contain 'mdv.visitRequire('");
        }
        if (!output.contains("moduleName")) {
            throw new AssertionError("Output should contain the module name");
        }
        if (!output.contains("1.0")) {
            throw new AssertionError("Output should contain the version string");
        }

        // Test with null module and version (should not throw, just produce output)
        asmifier.visitRequire(null, 0, null);
        text = asmifier.getText();
        output = text.get(text.size() - 1);
        if (!output.contains("mdv.visitRequire(")) {
            throw new AssertionError("Output should contain 'mdv.visitRequire(' even with null inputs");
        }

        // Test with negative access flags (should be accepted and produce output)
        asmifier.visitRequire("mod", -1, "v");
        text = asmifier.getText();
        output = text.get(text.size() - 1);
        if (!output.contains("mdv.visitRequire(")) {
            throw new AssertionError("Output should contain 'mdv.visitRequire(' with negative access");
        }

        System.out.println("ASMifier.visitRequire tests passed.");
    }
}
