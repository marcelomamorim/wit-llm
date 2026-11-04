/*
 * @test
 * @summary Test visitAnnotationDefault method of ASMifier for basic behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        ASMifier returned = asmifier.visitAnnotationDefault();
        if (returned == null) {
            throw new AssertionError("visitAnnotationDefault() returned null");
        }
        if (returned.getText() == null) {
            throw new AssertionError("Returned ASMifier getText() is null");
        }
        // The returned ASMifier should be a different instance than the original
        if (returned == asmifier) {
            throw new AssertionError("visitAnnotationDefault() should return a new ASMifier instance");
        }
    }
}
