/*
 * @test
 * @summary Test ASMifier.visitAnnotation method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        // Create ASMifier instance with public constructor
        ASMifier asmifier = new ASMifier();

        // Call visitAnnotation with normal strings
        ASMifier result = asmifier.visitAnnotation("TestName", "TestDesc");
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null");
        }

        // Call visitAnnotation with empty strings
        ASMifier resultEmpty = asmifier.visitAnnotation("", "");
        if (resultEmpty == null) {
            throw new AssertionError("visitAnnotation returned null for empty strings");
        }

        // Call visitAnnotation with null strings (allowed by signature, test behavior)
        // The method does not explicitly check for null, so test if it throws or not
        try {
            ASMifier resultNull = asmifier.visitAnnotation(null, null);
            if (resultNull == null) {
                throw new AssertionError("visitAnnotation returned null for null inputs");
            }
        } catch (NullPointerException e) {
            // Acceptable if NPE is thrown due to appendConstant or buf.append
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null inputs: " + e);
        }
    }
}
