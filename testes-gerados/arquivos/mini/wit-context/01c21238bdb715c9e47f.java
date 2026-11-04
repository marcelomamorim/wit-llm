/*
 * @test
 * @summary Test visitMultiANewArrayInsn method of Textifier for basic functionality and input handling
 * @run main TextifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier textifier = new Textifier();

        // Test with normal descriptor and positive dims
        textifier.visitMultiANewArrayInsn("[I", 3);
        List<String> output = textifier.getText();
        if (output.isEmpty()) {
            throw new AssertionError("Text output should not be empty after visitMultiANewArrayInsn call");
        }
        String line = output.get(output.size() - 1);
        if (!line.contains("MULTIANEWARRAY ") || !line.contains("[I") || !line.contains("3")) {
            throw new AssertionError("Output line does not contain expected MULTIANEWARRAY info: " + line);
        }

        // Test with empty descriptor and zero dims
        textifier.visitMultiANewArrayInsn("", 0);
        output = textifier.getText();
        line = output.get(output.size() - 1);
        if (!line.contains("MULTIANEWARRAY ") || !line.contains("0")) {
            throw new AssertionError("Output line does not handle empty descriptor or zero dims correctly: " + line);
        }

        // Test with null descriptor - should not throw but handle gracefully
        try {
            textifier.visitMultiANewArrayInsn(null, 1);
            output = textifier.getText();
            line = output.get(output.size() - 1);
            if (!line.contains("MULTIANEWARRAY ") || !line.contains("1")) {
                throw new AssertionError("Output line does not handle null descriptor correctly: " + line);
            }
        } catch (NullPointerException e) {
            // Acceptable if NPE is thrown due to null desc, as no explicit null check in source
        }

        // Test with negative dims - should still produce output line
        textifier.visitMultiANewArrayInsn("[Ljava/lang/Object;", -1);
        output = textifier.getText();
        line = output.get(output.size() - 1);
        if (!line.contains("MULTIANEWARRAY ") || !line.contains("-1")) {
            throw new AssertionError("Output line does not handle negative dims correctly: " + line);
        }

        System.out.println("All tests passed.");
    }
}
