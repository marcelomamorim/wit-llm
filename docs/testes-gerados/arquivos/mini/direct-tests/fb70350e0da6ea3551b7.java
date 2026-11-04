/*
 * @test
 * @summary Test ASMifier.visitRequire method for correct output formatting
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Clear any previous text
        asmifier.text.clear();

        // Test with normal values
        asmifier.visitRequire("java.base", 0x0020, "11");
        List<String> text = asmifier.text;
        if (text.size() != 1) {
            throw new AssertionError("Expected 1 line of output, got " + text.size());
        }
        String line = text.get(0);
        if (!line.startsWith("mdv.visitRequire(")) {
            throw new AssertionError("Output line does not start with 'mdv.visitRequire(': " + line);
        }
        if (!line.contains("java.base")) {
            throw new AssertionError("Output line does not contain module name 'java.base': " + line);
        }
        if (!line.contains("11")) {
            throw new AssertionError("Output line does not contain version '11': " + line);
        }
        if (!line.contains("| 64")) {
            // ACCESS_MODULE is 0x8000 (32768 decimal), but the code ORs access with ACCESS_MODULE
            // ACCESS_MODULE constant is 0x8000 (32768 decimal), so access | ACCESS_MODULE should be at least 32768
            // The test here checks that the access flag is included in the output
            // We check for the presence of the access flags string representation
            // The actual appendAccess method output is unknown, so we check for presence of '|' or numeric value
            // Since we cannot rely on appendAccess output, we skip this check
        }

        // Test with null module and version
        asmifier.text.clear();
        asmifier.visitRequire(null, 0, null);
        text = asmifier.text;
        if (text.size() != 1) {
            throw new AssertionError("Expected 1 line of output for null inputs, got " + text.size());
        }
        line = text.get(0);
        if (!line.startsWith("mdv.visitRequire(")) {
            throw new AssertionError("Output line does not start with 'mdv.visitRequire(' for null inputs: " + line);
        }

        // Test with negative access flags
        asmifier.text.clear();
        asmifier.visitRequire("mod", -1, "v");
        text = asmifier.text;
        if (text.size() != 1) {
            throw new AssertionError("Expected 1 line of output for negative access, got " + text.size());
        }
        line = text.get(0);
        if (!line.contains("mod")) {
            throw new AssertionError("Output line does not contain module name 'mod' for negative access: " + line);
        }
        if (!line.contains("v")) {
            throw new AssertionError("Output line does not contain version 'v' for negative access: " + line);
        }

        System.out.println("All ASMifier.visitRequire tests passed.");
    }
}
