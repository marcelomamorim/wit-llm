/*
 * @test
 * @summary Test basic behavior of ASMifier.visitFieldEnd()
 * @run main ASMifierWitupTest
 */

import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Clear any previous state
        asmifier.text.clear();

        // Call visitFieldEnd and check observable effects
        asmifier.visitFieldEnd();

        // The text list should contain exactly one string ending with ".visitEnd();\n"
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 but was " + asmifier.text.size());
        }

        String output = asmifier.text.get(0);
        if (!output.endsWith(".visitEnd();\n")) {
            throw new AssertionError("Output does not end with '.visitEnd();\n': " + output);
        }

        // Also check that the output starts with the 'name' field value
        // 'name' is protected final String initialized in ASMifier constructor to "this" by default
        // but since we use public ASMifier(), name is "this"
        // So output should start with "this"
        if (!output.startsWith("this")) {
            throw new AssertionError("Output does not start with 'this': " + output);
        }

        System.out.println("ASMifier.visitFieldEnd() test passed.");
    }
}
