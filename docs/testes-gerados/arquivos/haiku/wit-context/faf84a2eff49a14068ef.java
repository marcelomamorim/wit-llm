/*
 * @test
 * @summary Verifies AnnotationVisitor(int api) accepts supported ASM API versions, delegates with a null visitor, and rejects unsupported api values
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main AnnotationVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class AnnotationVisitorWitupTest {

    static class Probe extends AnnotationVisitor {
        Probe(int api) {
            super(api);
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

    public static void main(String[] args) {
        int[] supported = { Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6 };
        for (int api : supported) {
            Probe p = new Probe(api);
            check(p.apiValue() == api, "api not stored for " + api);
            check(p.delegate() == null, "delegate should be null for " + api);
        }

        boolean thrown = false;
        try {
            new Probe(0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "unsupported api 0 should throw IllegalArgumentException");
    }
}
