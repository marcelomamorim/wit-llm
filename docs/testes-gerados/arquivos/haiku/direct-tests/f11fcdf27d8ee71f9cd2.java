/*
 * @test
 * @summary Checks the public contract of ASMifier.visitAnnotationDefault(): it returns a new ASMifier and does not throw on repeated calls.
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testReturnsNonNullAsmifier();
        testReturnsNewInstanceEachCall();
        testRepeatedCallsDoNotThrow();
    }

    static void testReturnsNonNullAsmifier() {
        ASMifier printer = new ASMifier();
        ASMifier result = printer.visitAnnotationDefault();
        if (result == null) {
            throw new AssertionError("visitAnnotationDefault() returned null");
        }
        if (result == printer) {
            throw new AssertionError("visitAnnotationDefault() returned the same visitor instance");
        }
    }

    static void testReturnsNewInstanceEachCall() {
        ASMifier printer = new ASMifier();
        ASMifier first = printer.visitAnnotationDefault();
        ASMifier second = printer.visitAnnotationDefault();
        if (first == second) {
            throw new AssertionError("Repeated calls returned the same ASMifier instance");
        }
    }

    static void testRepeatedCallsDoNotThrow() {
        ASMifier printer = new ASMifier();
        for (int i = 0; i < 3; i++) {
            if (printer.visitAnnotationDefault() == null) {
                throw new AssertionError("Call " + i + " returned null");
            }
        }
    }
}
