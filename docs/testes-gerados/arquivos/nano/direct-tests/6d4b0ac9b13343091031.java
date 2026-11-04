/* @test    @summary Test ASMifier.visitMethodEnd() method for correct behavior    @run main ASMifierWitupTest */

import java.util.ArrayList;
import java.util.List;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitMethodEnd();
    }

    public static void testVisitMethodEnd() {
        // Create an anonymous subclass of ASMifier to access protected method
        jdk.internal.org.objectweb.asm.util.ASMifier asmifier = new jdk.internal.org.objectweb.asm.util.ASMifier() {
            @Override
            public void visitMethodEnd() {
                super.visitMethodEnd();
            }
        };
        // Prepare internal state
        asmifier.name = "TestMethod";
        asmifier.buf = new StringBuilder();
        asmifier.text = new ArrayList<>();
        // Call the method
        asmifier.visitMethodEnd();
        // Verify the output
        String result = asmifier.text.get(0);
        String expected = "TestMethod.visitEnd();\n";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
