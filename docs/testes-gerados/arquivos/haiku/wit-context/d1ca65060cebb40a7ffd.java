/*
 * @test
 * @summary Smoke and state-reset tests for Textifier.visitIntInsn using public API
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    private static final int BIPUSH = 16;
    private static final int NEWARRAY = 188;
    private static final int T_INT = 10;

    public static void main(String[] args) {
        testValidBipushDoesNotThrow();
        testNewarrayWithValidTypeDoesNotThrow();
        testRepeatedCallsOnSameInstanceDoNotThrow();
        System.out.println("TextifierWitupTest: PASSED");
    }

    static void testValidBipushDoesNotThrow() {
        Textifier t = new Textifier();
        t.visitIntInsn(BIPUSH, 42);
    }

    static void testNewarrayWithValidTypeDoesNotThrow() {
        Textifier t = new Textifier();
        t.visitIntInsn(NEWARRAY, T_INT);
    }

    static void testRepeatedCallsOnSameInstanceDoNotThrow() {
        Textifier t = new Textifier();
        t.visitIntInsn(BIPUSH, 1);
        t.visitIntInsn(BIPUSH, 2);
        t.visitIntInsn(NEWARRAY, T_INT);
        t.visitIntInsn(BIPUSH, 3);
    }
}
