/*
 * @test
 * @summary Tests the readBoolean method of java.io.RandomAccessFile for correct behavior.
 * @run main RandomAccessFileWitupTest_P331fc03c36ec
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P331fc03c36ec {

    public static void main(String[] args) throws Exception {
        testReadBooleanReturnsTrueWhenByteIsNonZero();
        testReadBooleanReturnsFalseWhenByteIsZero();
        testReadBooleanThrowsEOFExceptionAtEndOfFile();
    }

    private static void testReadBooleanReturnsTrueWhenByteIsNonZero() throws IOException {
        byte[] data = {1};
        try (InputStream is = new ByteArrayInputStream(data);)
        {
            RandomAccessFile raf = createRandomAccessFileFromInputStream(is);
            boolean result = raf.readBoolean();
            assert result == true : "Expected true when byte is non-zero";
        }
    }

    private static void testReadBooleanReturnsFalseWhenByteIsZero() throws IOException {
        byte[] data = {0};
        try (InputStream is = new ByteArrayInputStream(data);)
        {
            RandomAccessFile raf = createRandomAccessFileFromInputStream(is);
            boolean result = raf.readBoolean();
            assert result == false : "Expected false when byte is zero";
        }
    }

    private static void testReadBooleanThrowsEOFExceptionAtEndOfFile() throws IOException {
        byte[] data = {};
        try (InputStream is = new ByteArrayInputStream(data);)
        {
            RandomAccessFile raf = createRandomAccessFileFromInputStream(is);
            try {
                raf.readBoolean();
                assert false : "Expected EOFException to be thrown";
            } catch (EOFException e) {
                // expected
            }
        }
    }

    private static RandomAccessFile createRandomAccessFileFromInputStream(InputStream is) throws IOException {
        // Since RandomAccessFile does not have a public constructor from InputStream,
        // and the source code shows constructors from String and File, this mock method
        // is used as a substitute for testing. In real tests, use actual files.
        // For the purpose of this test, we simulate by writing to a temporary file.
        java.io.File tempFile = java.io.File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile)) {
            int b;
            while ((b = is.read()) != -1) {
                fos.write(b);
            }
        }
        return new RandomAccessFile(tempFile, "r");
    }
}
