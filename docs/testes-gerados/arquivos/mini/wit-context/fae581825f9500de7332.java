/*
 * @test
 * @summary Test ClassFileAssembler.cpi() method for correct exception and return behavior
 * @run main ClassFileAssemblerWitupTest
 */

public class ClassFileAssemblerWitupTest {
    public static void main(String[] args) {
        testCpiThrowsWhenCpIdxZero();
        testCpiReturnsCpIdxWhenNonZero();
    }

    private static void testCpiThrowsWhenCpIdxZero() {
        jdk.internal.reflect.ClassFileAssembler assembler = new jdk.internal.reflect.ClassFileAssembler();
        // cpIdx is initially 0, so cpi() should throw RuntimeException
        try {
            assembler.cpi();
            throw new RuntimeException("Expected RuntimeException not thrown by cpi() when cpIdx == 0");
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Illegal use of ClassFileAssembler")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testCpiReturnsCpIdxWhenNonZero() {
        jdk.internal.reflect.ClassFileAssembler assembler = new jdk.internal.reflect.ClassFileAssembler();
        // Use emitConstantPoolUTF8 to increment cpIdx to 1
        assembler.emitConstantPoolUTF8("");
        short cpIndex = assembler.cpi();
        if (cpIndex != 1) {
            throw new RuntimeException("cpi() returned " + cpIndex + ", expected 1 after emitting one constant pool entry");
        }
    }
}
