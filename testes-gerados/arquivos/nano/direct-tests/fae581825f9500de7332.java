/* @test    @summary Test for ClassFileAssembler.cpi() method behavior    @run main ClassFileAssemblerWitupTest */

import jdk.internal.reflect.ClassFileAssembler;

public class ClassFileAssemblerWitupTest {

    public static void main(String[] args) {
        testCpiWithInitialValueZero();
        testCpiAfterEmittingConstantPoolEntry();
    }

    private static void testCpiWithInitialValueZero() {
        ClassFileAssembler assembler = new ClassFileAssembler();
        boolean exceptionThrown = false;
        try {
            assembler.cpi();
        } catch (RuntimeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException when cpi() called with cpIdx == 0");
        }
    }

    private static void testCpiAfterEmittingConstantPoolEntry() {
        ByteVectorMock vec = new ByteVectorMock();
        ClassFileAssembler assembler = new ClassFileAssembler(vec);
        // Emulate emitting a UTF8 constant pool entry to increment cpIdx
        assembler.emitConstantPoolUTF8("TestString");
        short index = assembler.cpi();
        if (index != 1) {
            throw new AssertionError("Expected cpi() to return 1 after one constant pool entry, but got: " + index);
        }
    }

    // Mock class for ByteVector to satisfy constructor
    static class ByteVectorMock extends jdk.internal.util.ByteVector {
        private byte[] data = new byte[10];
        private int length = 0;
        public ByteVectorMock() {
            super();
        }
        @Override
        public void add(byte b) {
            if (length >= data.length) {
                byte[] newData = new byte[data.length * 2];
                System.arraycopy(data, 0, newData, 0, data.length);
                data = newData;
            }
            data[length++] = b;
        }
        @Override
        public int getLength() {
            return length;
        }
        @Override
        public byte get(int index) {
            if (index < 0 || index >= length) {
                throw new IndexOutOfBoundsException();
            }
            return data[index];
        }
        public void put(int index, byte b) {
            if (index < 0 || index >= length) {
                throw new IndexOutOfBoundsException();
            }
            data[index] = b;
        }
    }
}
