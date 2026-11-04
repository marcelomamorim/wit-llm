/*
 * @test
 * @summary Test ASMifier.visitMultiANewArrayInsn method for correct behavior
 * @run main ASMifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMultiANewArrayInsn();
    }

    public static void testVisitMultiANewArrayInsn() {
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier();
        List<String> textOutput = new ArrayList<>();
        // Override the 'text' list to capture output
        asmifier.text = textOutput;
        // Call the method with sample parameters
        String desc = "[I"; // descriptor for int array
        int dims = 2;
        asmifier.visitMultiANewArrayInsn(desc, dims);
        // Verify the output contains expected substrings
        boolean containsMethodCall = false;
        for (String line : textOutput) {
            if (line.contains("ASMifier.visitMultiANewArrayInsn")) {
                containsMethodCall = true;
                // Check if line contains the descriptor and dims
                if (!line.contains(desc) || !line.contains(String.valueOf(dims))) {
                    throw new AssertionError("Output does not contain expected descriptor or dims: " + line);
                }
            }
        }
        if (!containsMethodCall) {
            throw new AssertionError("Method call not found in output: visitMultiANewArrayInsn");
        }
        System.out.println("testVisitMultiANewArrayInsn passed.");
    }
}
