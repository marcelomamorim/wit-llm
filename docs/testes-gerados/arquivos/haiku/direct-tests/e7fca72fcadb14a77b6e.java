/*
 * @test
 * @summary Verifies ClassVisitor constructor API version validation and delegate storage
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidApiVersions() {
        int[] versions = { Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6 };
        for (int v : versions) {
            ClassVisitor cv = new ClassVisitor(v, null) {
            };
            check(cv.api == v, "api field not stored for version " + v);
            check(cv.cv == null, "delegate should be null for version " + v);
        }
    }

    static void testDelegateStored() {
        ClassVisitor delegate = new ClassVisitor(Opcodes.ASM5, null) {
        };
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, delegate) {
        };
        check(visitor.cv == delegate, "delegate was not stored");
        check(visitor.api == Opcodes.ASM5, "api was not stored");
    }

    static void testApiBelowRangeThrows() {
        boolean thrown = false;
        try {
            new ClassVisitor(Opcodes.ASM4 - 1, null) {
            };
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected for api below ASM4");
    }

    static void testApiAboveRangeThrows() {
        boolean thrown = false;
        try {
            new ClassVisitor(Opcodes.ASM6 + 1, null) {
            };
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected for api above ASM6");
    }

    public static void main(String[] args) {
        testValidApiVersions();
        testDelegateStored();
        testApiBelowRangeThrows();
        testApiAboveRangeThrows();
    }
}
