/*
 * @test
 * @summary Test ClassFileAssembler.cpi() method behavior
 * @run main ClassFileAssemblerWitupTest
 */

import jdk.internal.reflect.ClassFileAssembler;

public class ClassFileAssemblerWitupTest {
    public static void main(String[] args) {
        testCpiThrowsWhenNoConstantPoolEntryEmitted();
        testCpiReturnsCorrectValueAfterEmitConstantPoolUTF8();
        testCpiReturnsCorrectValueAfterEmitConstantPoolClass();
        testCpiReturnsCorrectValueAfterEmitConstantPoolNameAndType();
    }

    private static void testCpiThrowsWhenNoConstantPoolEntryEmitted() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        try {
            assembler.cpi();
            throw new RuntimeException("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Illegal use of ClassFileAssembler".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testCpiReturnsCorrectValueAfterEmitConstantPoolUTF8() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        assembler.emitConstantPoolUTF8("test");
        short cpi = assembler.cpi();
        if (cpi != 1) {
            throw new RuntimeException("cpi() returned " + cpi + ", expected 1 after one UTF8 entry");
        }
    }

    private static void testCpiReturnsCorrectValueAfterEmitConstantPoolClass() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        assembler.emitConstantPoolUTF8("test");
        assembler.emitConstantPoolClass((short)1);
        short cpi = assembler.cpi();
        if (cpi != 2) {
            throw new RuntimeException("cpi() returned " + cpi + ", expected 2 after two entries");
        }
    }

    private static void testCpiReturnsCorrectValueAfterEmitConstantPoolNameAndType() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        assembler.emitConstantPoolUTF8("name");
        assembler.emitConstantPoolUTF8("type");
        assembler.emitConstantPoolNameAndType((short)1, (short)2);
        short cpi = assembler.cpi();
        if (cpi != 3) {
            throw new RuntimeException("cpi() returned " + cpi + ", expected 3 after three entries");
        }
    }
}
