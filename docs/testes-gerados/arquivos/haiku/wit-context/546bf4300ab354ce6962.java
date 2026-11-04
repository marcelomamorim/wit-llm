/*
 * @test
 * @summary Verify ModuleVisitor(int, ModuleVisitor) accepts Opcodes.ASM6 and rejects other API versions with IllegalArgumentException
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {

    static class Probe extends ModuleVisitor {
        Probe(int api, ModuleVisitor mv) {
            super(api, mv);
        }

        int apiValue() {
            return api;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void assertRejected(int api, ModuleVisitor mv) {
        try {
            new Probe(api, mv);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for api=" + api);
    }

    public static void main(String[] args) {
        Probe withoutDelegate = new Probe(Opcodes.ASM6, null);
        check(withoutDelegate.apiValue() == Opcodes.ASM6,
              "api must be stored when Opcodes.ASM6 is used");

        Probe delegate = new Probe(Opcodes.ASM6, new Probe(Opcodes.ASM6, null));
        check(delegate.apiValue() == Opcodes.ASM6,
              "api must be stored when a non-null delegate is provided");

        assertRejected(Opcodes.ASM6 + 1, null);
        assertRejected(Opcodes.ASM6 - 1, null);
        assertRejected(0, null);
        assertRejected(Opcodes.ASM6 + 1, new Probe(Opcodes.ASM6, null));
    }
}
