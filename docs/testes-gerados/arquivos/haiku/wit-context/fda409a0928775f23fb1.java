/*
 * @test
 * @summary Verifies the API version validation of the AnnotationVisitor(int, AnnotationVisitor) constructor
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    public static void main(String[] args) {
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            new AnnotationVisitor(api, null) {};
            AnnotationVisitor delegate = new AnnotationVisitor(api, null) {};
            new AnnotationVisitor(api, delegate) {};
        }

        expectIllegalArgument(Opcodes.ASM4 - 1, null);
        expectIllegalArgument(Opcodes.ASM6 + 1, null);
        expectIllegalArgument(0, null);
        expectIllegalArgument(-1, null);

        AnnotationVisitor delegate = new AnnotationVisitor(Opcodes.ASM5, null) {};
        expectIllegalArgument(Opcodes.ASM4 - 1, delegate);
        expectIllegalArgument(Opcodes.ASM6 + 1, delegate);
    }

    private static void expectIllegalArgument(int api, AnnotationVisitor av) {
        try {
            new AnnotationVisitor(api, av) {};
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for api=" + api);
    }
}
