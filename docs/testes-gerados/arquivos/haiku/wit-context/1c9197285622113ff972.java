/*
 * @test
 * @summary Verifies the public single-argument ClassVisitor constructor for supported ASM API versions
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static final class Probe extends ClassVisitor {
        Probe(int api) {
            super(api);
        }

        int exposedApi() {
            return api;
        }

        boolean hasDelegate() {
            return cv != null;
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void checkApi(int api) {
        Probe p = new Probe(api);
        if (p.exposedApi() != api) {
            fail("api mismatch for " + api + ": " + p.exposedApi());
        }
        if (p.hasDelegate()) {
            fail("single-argument constructor must not set a delegate, api=" + api);
        }
    }

    public static void main(String[] args) {
        checkApi(Opcodes.ASM4);
        checkApi(Opcodes.ASM5);
        checkApi(Opcodes.ASM6);
    }
}
