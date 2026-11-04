/*
 * @test
 * @summary Verifies that the public ClassVisitor(int api) constructor stores the api version and delegates with a null cv.
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static class Probe extends ClassVisitor {
        Probe(int api) {
            super(api);
        }

        int apiValue() {
            return api;
        }

        ClassVisitor delegate() {
            return cv;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Probe p4 = new Probe(Opcodes.ASM4);
        check(p4.apiValue() == Opcodes.ASM4, "api field must be ASM4");
        check(p4.delegate() == null, "cv must be null after one-arg constructor");

        Probe p5 = new Probe(Opcodes.ASM5);
        check(p5.apiValue() == Opcodes.ASM5, "api field must be ASM5");
        check(p5.delegate() == null, "cv must be null for ASM5 visitor");

        Probe p6 = new Probe(Opcodes.ASM6);
        check(p6.apiValue() == Opcodes.ASM6, "api field must be ASM6");
        check(p6.delegate() == null, "cv must be null for ASM6 visitor");

        ClassVisitor anon = new ClassVisitor(Opcodes.ASM5) {
        };
        check(anon != null, "anonymous subclass must be constructed");
    }
}
