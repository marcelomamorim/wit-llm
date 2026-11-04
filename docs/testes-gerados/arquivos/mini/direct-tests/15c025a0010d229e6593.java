/*
 * @test
 * @summary Test ASMifier.visitFieldEnd() method behavior
 * @run main ASMifierWitupTest
 */

import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Before visitFieldEnd, text list should be empty
        if (!asmifier.text.isEmpty()) {
            throw new AssertionError("text list should be empty initially");
        }

        // Call visitFieldEnd
        asmifier.visitFieldEnd();

        // After visitFieldEnd, text list should have exactly one element
        if (asmifier.text.size() != 1) {
            throw new AssertionError("text list should contain exactly one element after visitFieldEnd");
        }

        // The element should be "name.visitEnd();\n" where name is asmifier.name
        String expected = asmifier.name + ".visitEnd();\n";
        String actual = asmifier.text.get(0);
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }

        // The buf StringBuilder should be reset to length 0 after visitFieldEnd
        if (asmifier.buf.length() != 0) {
            throw new AssertionError("buf length should be 0 after visitFieldEnd");
        }

        System.out.println("ASMifier.visitFieldEnd() test passed.");
    }
}
