/*
 * @test
 * @summary Test ClassVisitor constructor with api argument for valid and invalid values
 * @run main ClassVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class ClassVisitorWitupTest {

    public static void main(String[] args) {
        testValidApiValues();
        testInvalidApiValues();
    }

    private static void testValidApiValues() {
        // Valid api values: ASM4, ASM5, ASM6
        int[] validApis = {Opcodes.ASM4, Opcodes.ASM5, Opcodes.ASM6};
        for (int api : validApis) {
            ClassVisitor cv = new ClassVisitor(api) {
                // Provide empty implementation for abstract class
            };
            if (cv == null) {
                throw new AssertionError("ClassVisitor instance is null for api: " + api);
            }
        }
    }

    private static void testInvalidApiValues() {
        // Invalid api values: less than ASM4 and greater than ASM6
        int[] invalidApis = {Opcodes.ASM4 - 1, Opcodes.ASM6 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int api : invalidApis) {
            try {
                new ClassVisitor(api) {
                    // Provide empty implementation for abstract class
                };
                throw new AssertionError("Expected IllegalArgumentException for api: " + api);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            } catch (Throwable t) {
                throw new AssertionError("Unexpected exception for api: " + api + ": " + t);
            }
        }
    }
}
