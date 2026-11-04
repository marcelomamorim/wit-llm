/*
 * @test    @summary Test getEntry method of ZipFile for existing and non-existing entries
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.IOException;

public class ZipFileWitupTest {

    public static void main(String[] args) throws IOException {
        testGetEntryExisting();
        testGetEntryNonExisting();
    }

    static void testGetEntryExisting() throws IOException {
        // Create a temporary zip file with one entry
        File tempZip = File.createTempFile("test", ".zip");
        tempZip.deleteOnExit();
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(tempZip))) {
            ZipEntry entry = new ZipEntry("testEntry");
            zos.putNextEntry(entry);
            zos.write("test".getBytes());
            zos.closeEntry();
        }
        try (ZipFile zipFile = new ZipFile(tempZip)) {
            ZipEntry e = zipFile.getEntry("testEntry");
            if (e == null) {
                throw new AssertionError("Expected entry not found");
            }
            if (!"testEntry".equals(e.getName())) {
                throw new AssertionError("Entry name mismatch");
            }
        }
    }

    static void testGetEntryNonExisting() throws IOException {
        // Create a temporary zip file without the target entry
        File tempZip = File.createTempFile("test", ".zip");
        tempZip.deleteOnExit();
        try (java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(tempZip))) {
            ZipEntry entry = new ZipEntry("anotherEntry");
            zos.putNextEntry(entry);
            zos.write("data".getBytes());
            zos.closeEntry();
        }
        try (ZipFile zipFile = new ZipFile(tempZip)) {
            ZipEntry e = zipFile.getEntry("nonExistingEntry");
            if (e != null) {
                throw new AssertionError("Expected null for non-existing entry");
            }
        }
    }
}
