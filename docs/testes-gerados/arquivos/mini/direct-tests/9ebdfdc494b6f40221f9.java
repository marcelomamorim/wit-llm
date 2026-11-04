/*
 * @test
 * @summary Test ASMifier.visitAnnotationEnd() method behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        // Clear text list before call
        asmifier.text.clear();
        asmifier.visitAnnotationEnd();
        // After visitAnnotationEnd, text list should contain exactly one string
        if (asmifier.text.size() != 1) {
            throw new AssertionError("Expected text size 1 but was " + asmifier.text.size());
        }
        String line = asmifier.text.get(0);
        // The line should be exactly "av0.visitEnd();\n" because id is 0 by default
        if (!"av0.visitEnd();\n".equals(line)) {
            throw new AssertionError("Unexpected text content: " + line);
        }
    }
}
