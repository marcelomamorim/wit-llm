/*
 * @test
 * @summary Test ASMifier.visitAnnotationDefault() method behavior
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

        // The returned ASMifier should be a new instance, not the same as the caller
        if (returned == asmifier) {
            throw new AssertionError("visitAnnotationDefault() should return a new ASMifier instance, not the same one");
        }

        // The text list of the returned ASMifier should not be empty
        if (returned.getText() == null || returned.getText().isEmpty()) {
            throw new AssertionError("Returned ASMifier's text should not be null or empty");
        }

        // The original ASMifier's text should contain the visitAnnotationDefault call string
        boolean foundCall = false;
        for (String line : asmifier.getText()) {
            if (line.contains(".visitAnnotationDefault();")) {
                foundCall = true;
                break;
            }
        }
        if (!foundCall) {
            throw new AssertionError("Original ASMifier's text should contain visitAnnotationDefault call string");
        }
    }
}
