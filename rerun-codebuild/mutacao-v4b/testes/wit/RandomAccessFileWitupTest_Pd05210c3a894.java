/* @test
   @summary Tests readFully method for normal and exceptional behavior.
   @run main RandomAccessFileWitupTest_Pd05210c3a894
 */
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class RandomAccessFileWitupTest_Pd05210c3a894 {
    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    private static void testReadFullyNormal() throws Exception {
        byte[] buffer = new byte[10];
        try (RandomAccessFile raf = new RandomAccessFile("testfile", "rw")) {
            // Write some data to the file
            raf.write(new byte[] {1,2,3,4,5,6,7,8,9,10});
            raf.seek(0);
            // Read data fully
            raf.readFully(buffer, 0, buffer.length);
            boolean allMatch = true;
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != (byte)(i+1)) {
                    allMatch = false;
                    break;
                }
            }
            if (!allMatch) {
                throw new AssertionError("Data read does not match data written.");
            }
        }
    }

    private static void testReadFullyEOF() throws Exception {
        byte[] buffer = new byte[10];
        try (RandomAccessFile raf = new RandomAccessFile("testfile", "r")) {
            // Write some data to the file
            raf.write(new byte[] {1,2,3});
            raf.seek(0);
            // Read more data than available to trigger EOFException
            try {
                raf.readFully(buffer, 0, buffer.length);
                throw new AssertionError("Expected EOFException was not thrown.");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
