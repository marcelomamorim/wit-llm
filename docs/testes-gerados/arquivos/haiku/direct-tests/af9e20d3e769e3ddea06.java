/*
 * @test
 * @summary Verifies that MethodVisitor(int api) builds a visitor with no delegate, so calls to visitMaxs and visitEnd complete without error, and that subclass overrides are dispatched.
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class MethodVisitorWitupTest {
    public static void main(String[] args) {
        MethodVisitor mv = new MethodVisitor(0x50000) { };
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        final int[] count = {0};
        MethodVisitor counting = new MethodVisitor(0x50000) {
            @Override
            public void visitEnd() {
                count[0]++;
            }
        };
        counting.visitEnd();
        counting.visitMaxs(0, 0);
        if (count[0] != 1) {
            throw new AssertionError("expected visitEnd override to be called once, was " + count[0]);
        }
    }
}
