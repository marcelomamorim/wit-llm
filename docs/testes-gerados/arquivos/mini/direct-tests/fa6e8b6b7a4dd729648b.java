/*
 * @test
 * @summary Test ASMifier.visitPackage method behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal package name
        asmifier.visitPackage("java/lang");
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list should not be empty after visitPackage");
        }
        String last = text.get(text.size() - 1);
        if (!last.startsWith("mdv.visitPackage(")) {
            throw new AssertionError("Output should start with 'mdv.visitPackage('");
        }
        if (!last.contains("java/lang")) {
            throw new AssertionError("Output should contain the package name 'java/lang'");
        }

        // Test with empty string
        asmifier.visitPackage("");
        last = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!last.contains("")) {
            throw new AssertionError("Output should contain empty string literal");
        }

        // Test with null (should not throw, but output null literal)
        asmifier.visitPackage(null);
        last = asmifier.getText().get(asmifier.getText().size() - 1);
        if (!last.contains("null")) {
            throw new AssertionError("Output should contain 'null' literal when input is null");
        }
    }
}
