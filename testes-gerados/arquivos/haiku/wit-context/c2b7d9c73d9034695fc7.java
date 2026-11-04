/*
 * @test
 * @summary Verifies that CheckMethodAdapter.visitCode rejects abstract methods and accepts non-abstract ones
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckMethodAdapterWitupTest
 */

import java.util.HashMap;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testAbstractMethodRejectsCode();
        testNonAbstractMethodAcceptsCode();
    }

    static void testAbstractMethodRejectsCode() {
        CheckMethodAdapter cma = new CheckMethodAdapter(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT, "m", "()V", null, new HashMap<>());
        try {
            cma.visitCode();
        } catch (RuntimeException e) {
            if (!"Abstract methods cannot have code".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("Expected RuntimeException for abstract method");
    }

    static void testNonAbstractMethodAcceptsCode() {
        CheckMethodAdapter cma = new CheckMethodAdapter(
                Opcodes.ACC_PUBLIC, "m", "()V", null, new HashMap<>());
        cma.visitCode();
    }
}
