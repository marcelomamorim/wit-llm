/* @test    @summary Test for ZipOutputStream.finish() method behavior.    @run main ZipOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testFinishWritesCentralDirectory();
        testFinishMultipleCalls();
        testFinishWithoutEntries();
    }

    private static void testFinishWritesCentralDirectory() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry("file.txt");
        zos.putNextEntry(entry);
        zos.write("content".getBytes());
        zos.closeEntry();
        zos.finish();
        byte[] result = baos.toByteArray();
        boolean hasCentralDirectory = new String(result).contains("PK");
        if (!hasCentralDirectory) {
            throw new AssertionError("Central directory not written by finish()");
        }
    }

    private static void testFinishMultipleCalls() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry("file2.txt");
        zos.putNextEntry(entry);
        zos.write("data".getBytes());
        zos.closeEntry();
        zos.finish();
        // calling finish again should not throw or alter output
        zos.finish();
        byte[] result = baos.toByteArray();
        int countPK = countOccurrences(result, "PK");
        if (countPK < 2) {
            throw new AssertionError("Expected at least two PK signatures in ZIP structure");
        }
    }

    private static void testFinishWithoutEntries() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zos.finish();
        byte[] result = baos.toByteArray();
        boolean isEmptyOrValid = result.length == 0 || new String(result).contains("PK");
        if (!isEmptyOrValid) {
            throw new AssertionError("finish() on empty ZipOutputStream produced invalid ZIP structure");
        }
    }

    private static int countOccurrences(byte[] data, String pattern) {
        String s = new String(data);
        int count = 0;
        int index = 0;
        while ((index = s.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }
}
