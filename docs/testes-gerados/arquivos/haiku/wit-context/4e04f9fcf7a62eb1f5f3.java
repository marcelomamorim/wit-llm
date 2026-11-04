/*
 * @test
 * @summary Verifies FieldVisitor(int) accepts supported ASM API versions and rejects unsupported ones with IllegalArgumentException
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main FieldVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        int[] validApis = { Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6 };
        for (int api : validApis) {
            FieldVisitor fv = new FieldVisitor(api) {};
            if (fv == null) {
                throw new RuntimeException("FieldVisitor was not created for api " + api);
            }
        }

        int[] invalidApis = { 0, -1, Opcodes.ASM6 + 1 };
        for (int api : invalidApis) {
            try {
                new FieldVisitor(api) {};
                throw new RuntimeException("Expected IllegalArgumentException for api " + api);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }
}
