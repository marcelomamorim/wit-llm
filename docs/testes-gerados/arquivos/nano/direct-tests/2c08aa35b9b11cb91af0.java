/* @test    @summary Test OutputStream.write(byte[]) method for correct delegation and behavior    @run main OutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteByteArrayDelegatesToWriteMethod();
        testWriteByteArrayWithEmptyArray();
        testWriteByteArrayNullThrows();
    }

    private static void testWriteByteArrayDelegatesToWriteMethod() {
        class TestOutputStream extends OutputStream {
            boolean writeCalled = false;
            byte[] writtenBytes = null;

            @Override
            public void write(byte[] b, int off, int len) {
                writeCalled = true;
                writtenBytes = new byte[len];
                System.arraycopy(b, off, writtenBytes, 0, len);
            }

            @Override
            public void write(int b) {
                // Not used in this test
            }
        }
        TestOutputStream tos = new TestOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        try {
            tos.write(data);
            assert tos.writeCalled : "write(byte[]) should delegate to write(byte[], int, int)";
            assert java.util.Arrays.equals(tos.writtenBytes, data) : "Data written does not match input";
        } catch (Exception e) {
            throw new AssertionError("Exception during testWriteByteArrayDelegatesToWriteMethod", e);
        }
    }

    private static void testWriteByteArrayWithEmptyArray() {
        class TestOutputStream extends OutputStream {
            boolean writeCalled = false;
            byte[] writtenBytes = null;

            @Override
            public void write(byte[] b, int off, int len) {
                writeCalled = true;
                writtenBytes = new byte[len];
                System.arraycopy(b, off, writtenBytes, 0, len);
            }

            @Override
            public void write(int b) {
                // Not used in this test
            }
        }
        TestOutputStream tos = new TestOutputStream();
        byte[] empty = {};
        try {
            tos.write(empty);
            assert tos.writeCalled : "write(byte[]) with empty array should delegate";
            assert tos.writtenBytes.length == 0 : "Written bytes length should be zero";
        } catch (Exception e) {
            throw new AssertionError("Exception during testWriteByteArrayWithEmptyArray", e);
        }
    }

    private static void testWriteByteArrayNullThrows() {
        class TestOutputStream extends OutputStream {
            @Override
            public void write(byte[] b, int off, int len) {
                // Not used in this test
            }
            @Override
            public void write(int b) {
                // Not used in this test
            }
        }
        TestOutputStream tos = new TestOutputStream();
        try {
            tos.write(null);
            throw new AssertionError("Expected NullPointerException for null byte array");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
