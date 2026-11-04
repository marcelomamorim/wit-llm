/*
 * @test
 * @summary Verifies the ModuleVisitor(int, ModuleVisitor) constructor accepts Opcodes.ASM6 and rejects other API values.
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ModuleVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ModuleVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ModuleVisitorWitupTest {

    static final class Probe extends ModuleVisitor {
        Probe(int api, ModuleVisitor mv) {
            super(api, mv);
        }

        int apiValue() {
            return api;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Probe withoutDelegate = new Probe(Opcodes.ASM6, null);
        check(withoutDelegate.apiValue() == Opcodes.ASM6,
              "api field must keep Opcodes.ASM6");

        Probe withDelegate = new Probe(Opcodes.ASM6, withoutDelegate);
        check(withDelegate.apiValue() == Opcodes.ASM6,
              "api field must keep Opcodes.ASM6 when a delegate is given");

        int[] invalidApis = {0, 1, 5, -1};
        for (int api : invalidApis) {
            boolean thrown = false;
            try {
                new Probe(api, null);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "expected IllegalArgumentException for api " + api);
        }
    }
}
