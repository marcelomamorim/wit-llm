/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.ASMifier.visitPackage(String) for expected behavior
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
            throw new AssertionError("Text list should not be empty after visitPackage call");
        }
        String lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("mdv.visitPackage(")) {
            throw new AssertionError("Output line should contain 'mdv.visitPackage('");
        }
        if (!lastLine.contains("java/lang")) {
            throw new AssertionError("Output line should contain the package name 'java/lang'");
        }

        // Test with empty string package name
        asmifier.visitPackage("");
        text = asmifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("mdv.visitPackage(")) {
            throw new AssertionError("Output line should contain 'mdv.visitPackage(' for empty package");
        }

        // Test with null package name
        // According to source, no explicit null check, so it should not throw but print null
        asmifier.visitPackage(null);
        text = asmifier.getText();
        lastLine = text.get(text.size() - 1);
        if (!lastLine.contains("mdv.visitPackage(")) {
            throw new AssertionError("Output line should contain 'mdv.visitPackage(' for null package");
        }
        if (!lastLine.contains("null")) {
            throw new AssertionError("Output line should contain 'null' string when package is null");
        }

        System.out.println("ASMifier.visitPackage tests passed.");
    }
}
