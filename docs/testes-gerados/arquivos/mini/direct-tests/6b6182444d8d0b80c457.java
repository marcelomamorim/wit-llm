/*
 * @test
 * @summary Test ASMifier.visitAnnotation method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with a normal descriptor and visible = true
        ASMifier result1 = asmifier.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (result1 == null) {
            throw new AssertionError("visitAnnotation returned null for visible=true");
        }

        // Test with a normal descriptor and visible = false
        ASMifier result2 = asmifier.visitAnnotation("Ljava/lang/SuppressWarnings;", false);
        if (result2 == null) {
            throw new AssertionError("visitAnnotation returned null for visible=false");
        }

        // Test with empty descriptor and visible = true
        ASMifier result3 = asmifier.visitAnnotation("", true);
        if (result3 == null) {
            throw new AssertionError("visitAnnotation returned null for empty descriptor");
        }

        // Test with null descriptor (allowed by method signature, no explicit check)
        ASMifier result4 = asmifier.visitAnnotation(null, true);
        if (result4 == null) {
            throw new AssertionError("visitAnnotation returned null for null descriptor");
        }
    }
}
