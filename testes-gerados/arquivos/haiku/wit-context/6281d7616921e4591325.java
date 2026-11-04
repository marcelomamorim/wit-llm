/*
 * @test
 * @summary Verify FieldVisitor(int, FieldVisitor) API version validation and delegate storage
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main FieldVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    static class Probe extends FieldVisitor {
        Probe(int api, FieldVisitor fv) {
            super(api, fv);
        }

        FieldVisitor delegate() {
            return fv;
        }
    }

    public static void main(String[] args) {
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            Probe p = new Probe(api, null);
            if (p.delegate() != null) {
                throw new RuntimeException("Expected null delegate for api " + api);
            }
        }

        Probe withDelegate = new Probe(Opcodes.ASM6, new Probe(Opcodes.ASM5, null));
        if (withDelegate.delegate() == null) {
            throw new RuntimeException("Delegate was not stored");
        }

        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, 0, -1};
        for (int api : invalidApis) {
            try {
                new Probe(api, null);
                throw new RuntimeException("Expected IllegalArgumentException for api " + api);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }
}
