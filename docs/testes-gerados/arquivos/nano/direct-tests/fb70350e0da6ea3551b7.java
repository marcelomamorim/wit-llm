/*
 * @test
 * @summary Test ASMifier.visitRequire method for correct output
 * @run main ASMifierWitupTest
 */
import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitRequireOutputs();
    }

    public static void testVisitRequireOutputs() {
        ASMifier asmifier = new ASMifier();
        List<String> outputLines = new ArrayList<>();
        // Override the text list to capture output
        asmifier.text = outputLines;
        // Call the method with sample parameters
        String moduleName = "java.base";
        int accessFlags = 0x0020; // example access flag
        String version = "11";
        asmifier.visitRequire(moduleName, accessFlags, version);
        // Verify the output contains expected substrings
        boolean containsModule = false;
        boolean containsAccess = false;
        boolean containsVersion = false;
        for (String line : outputLines) {
            if (line.contains("visitRequire")) {
                containsModule = line.contains(moduleName);
                containsAccess = line.contains("0x0020") || line.contains("32");
                containsVersion = line.contains(version);
            }
        }
        if (!containsModule || !containsAccess || !containsVersion) {
            throw new AssertionError("Output does not contain expected substrings. Output: " + outputLines);
        }
    }
}
