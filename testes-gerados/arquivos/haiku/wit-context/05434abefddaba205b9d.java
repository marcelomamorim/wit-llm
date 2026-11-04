/*
 * @test
 * @summary Verify observable behavior of RandomAccessFile.readFully(byte[]) for EOF and normal reads
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf-witup", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                byte[] data = {1, 2, 3, 4, 5};
                raf.write(data);

                raf.seek(0);
                byte[] buf = new byte[5];
                raf.readFully(buf);
                for (int i = 0; i < data.length; i++) {
                    if (buf[i] != data[i]) {
                        throw new RuntimeException("Mismatch at index " + i);
                    }
                }

                raf.seek(0);
                byte[] big = new byte[10];
                boolean eof = false;
                try {
                    raf.readFully(big);
                } catch (EOFException e) {
                    eof = true;
                }
                if (!eof) {
                    throw new RuntimeException("Expected EOFException when file has fewer bytes than buffer");
                }

                raf.seek(0);
                byte[] empty = new byte[0];
                raf.readFully(empty);
                if (raf.getFilePointer() != 0) {
                    throw new RuntimeException("Zero-length readFully must not advance the file pointer");
                }
            }
        } finally {
            f.delete();
        }
    }
}
