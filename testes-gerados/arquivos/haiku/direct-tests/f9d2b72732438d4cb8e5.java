/*
 * @test
 * @summary Checks API version validation in the public MethodVisitor(int, MethodVisitor) constructor
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main MethodVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MethodVisitorWitupTest {

    public static void main(String[] args) throws Exception {
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            MethodVisitor mv = new MethodVisitor(api, null) {};
            if (mv == null) {
                throw new AssertionError("constructor returned null for valid api " + api);
            }
            MethodVisitor mv2 = new MethodVisitor(api, mv) {};
            if (mv2 == null) {
                throw new AssertionError("constructor returned null with delegate for api " + api);
            }
        }

        int[] invalidApis = {
            0,
            1,
            Opcodes.ASM4 - 1,
            Opcodes.ASM6 + 1,
            7,
            Integer.MAX_VALUE,
            -1
        };
        for (int api : invalidApis) {
            checkRejected(api, null);
            checkRejected(api, new MethodVisitor(Opcodes.ASM6) {});
        }
    }

    private static void checkRejected(int api, MethodVisitor delegate) {
        try {
            new MethodVisitor(api, delegate) {};
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for api " + api);
    }
}
