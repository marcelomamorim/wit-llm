/*
 * @test
 * @summary Verifies FieldVisitor(int, FieldVisitor) accepts ASM4..ASM6 and rejects values outside that range
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

        int apiValue() {
            return this.api;
        }

        FieldVisitor delegate() {
            return this.fv;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(int api, FieldVisitor fv) {
        try {
            new Probe(api, fv);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for api=" + api);
    }

    public static void main(String[] args) {
        int[] valid = { Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6 };
        for (int v : valid) {
            Probe p = new Probe(v, null);
            check(p.apiValue() == v, "api not stored for " + v);
            check(p.delegate() == null, "null delegate not preserved for " + v);
        }

        FieldVisitor delegate = new Probe(Opcodes.ASM5, null);
        Probe withDelegate = new Probe(Opcodes.ASM6, delegate);
        check(withDelegate.delegate() == delegate, "delegate not stored");
        check(withDelegate.apiValue() == Opcodes.ASM6, "api not stored with delegate");

        expectIllegalArgument(Opcodes.ASM4 - 1, null);
        expectIllegalArgument(Opcodes.ASM6 + 1, null);
        expectIllegalArgument(0, delegate);
    }
}
