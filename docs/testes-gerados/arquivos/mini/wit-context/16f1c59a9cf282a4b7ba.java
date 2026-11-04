/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.ASMifier.visitUse(String) for basic behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Call visitUse with a normal string
        asmifier.visitUse("TestService");

        // The text list should contain the expected string
        List<String> text = asmifier.getText();
        if (text.isEmpty()) {
            throw new AssertionError("Text list is empty after visitUse call");
        }

        String lastLine = text.get(text.size() - 1);
        String expectedStart = "mdv.visitUse(";
        if (!lastLine.startsWith(expectedStart)) {
            throw new AssertionError("Output does not start with expected prefix: " + lastLine);
        }

        if (!lastLine.contains("TestService")) {
            throw new AssertionError("Output does not contain the service string: " + lastLine);
        }

        if (!lastLine.endsWith(");\n")) {
            throw new AssertionError("Output does not end with expected suffix: " + lastLine);
        }

        System.out.println("ASMifier.visitUse test passed.");
    }
}
