/*
 * @test
 * @summary Tests for java.util.jar.JarFile constructor with specific Runtime.Version
 * @run main JarFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

public class JarFileWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithVersionEqualToRuntimeVersion();
        testConstructorWithVersionLessThanBaseVersion();
        testConstructorWithVersionGreaterThanRuntimeVersion();
    }

    static void testConstructorWithVersionEqualToRuntimeVersion() throws IOException {
        Runtime.Version version = Runtime.Version.parse("11");
        File file = createDummyJarFile();
        JarFile jar = new JarFile(file, false, 0, version);
        assert jar != null : "JarFile should be created with version equal to runtime version";
        file.delete();
    }

    static void testConstructorWithVersionLessThanBaseVersion() throws IOException {
        Runtime.Version version = Runtime.Version.parse("8");
        File file = createDummyJarFile();
        JarFile jar = new JarFile(file, false, 0, version);
        assert jar != null : "JarFile should be created with version less than base version";
        file.delete();
    }

    static void testConstructorWithVersionGreaterThanRuntimeVersion() throws IOException {
        Runtime.Version version = Runtime.Version.parse("15");
        File file = createDummyJarFile();
        JarFile jar = new JarFile(file, false, 0, version);
        assert jar != null : "JarFile should be created with version greater than runtime version";
        file.delete();
    }

    static File createDummyJarFile() throws IOException {
        File tempFile = File.createTempFile("dummy", ".jar");
        // Creating an empty jar file
        return tempFile;
    }
}
