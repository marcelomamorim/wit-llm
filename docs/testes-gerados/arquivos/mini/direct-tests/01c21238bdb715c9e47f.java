/*
 * @test
 * @summary Test visitMultiANewArrayInsn method of Textifier
 * @run main TextifierWitupTest
 */

import java.util.List;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) throws Exception {
        Textifier textifier = new Textifier();

        // Test with normal descriptor and positive dims
        textifier.visitMultiANewArrayInsn("[Ljava/lang/String;", 3);
        List<String> text = textifier.getText();
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("MULTIANEWARRAY ") || !lastLine.contains("[Ljava/lang/String;") || !lastLine.contains("3")) {
            throw new AssertionError("visitMultiANewArrayInsn did not produce expected output for normal input");
        }

        // Test with empty descriptor and zero dims
        textifier.visitMultiANewArrayInsn("", 0);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("MULTIANEWARRAY ") || !lastLine.contains("0")) {
            throw new AssertionError("visitMultiANewArrayInsn did not produce expected output for empty descriptor and zero dims");
        }

        // Test with null descriptor (should not throw, but descriptor appended as "null")
        textifier.visitMultiANewArrayInsn(null, 1);
        text = textifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("MULTIANEWARRAY ") || !lastLine.contains("null") || !lastLine.contains("1")) {
            throw new AssertionError("visitMultiANewArrayInsn did not produce expected output for null descriptor");
        }

        System.out.println("All tests passed.");
    }
}
