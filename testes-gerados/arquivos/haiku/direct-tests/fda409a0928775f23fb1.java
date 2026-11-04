/*
 * @test
 * @summary Verifies AnnotationVisitor constructor API version validation and delegate storage
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    static class Probe extends AnnotationVisitor {
        Probe(int api, AnnotationVisitor av) {
            super(api, av);
        }

        int apiValue() {
            return api;
        }

        AnnotationVisitor delegate() {
            return av;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(int api) {
        try {
            new Probe(api, null);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for api=" + api);
    }

    public static void main(String[] args) {
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            Probe p = new Probe(api, null);
            check(p.apiValue() == api, "api not stored for " + api);
            check(p.delegate() == null, "null delegate not preserved for " + api);
        }

        AnnotationVisitor delegate = new Probe(Opcodes.ASM6, null);
        Probe withDelegate = new Probe(Opcodes.ASM5, delegate);
        check(withDelegate.delegate() == delegate, "delegate not stored");
        check(withDelegate.apiValue() == Opcodes.ASM5, "api mismatch with delegate");

        expectIllegalArgument(Opcodes.ASM4 - 1);
        expectIllegalArgument(Opcodes.ASM6 + 1);
    }
}
