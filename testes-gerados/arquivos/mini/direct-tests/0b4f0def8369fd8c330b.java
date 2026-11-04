/*
 * @test
 * @summary Test ImageReader.getModuleNames() method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.Node;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetModuleNamesReturnsExpectedModules();
        testGetModuleNamesThrowsWhenClosed();
    }

    private static void testGetModuleNamesReturnsExpectedModules() throws IOException {
        // Use the current running JDK image file path
        java.nio.file.Path imagePath = java.nio.file.Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            String[] moduleNames = reader.getModuleNames();

            if (moduleNames == null) {
                throw new AssertionError("getModuleNames() returned null");
            }

            if (moduleNames.length == 0) {
                throw new AssertionError("getModuleNames() returned empty array");
            }

            for (String name : moduleNames) {
                if (name == null || name.isEmpty()) {
                    throw new AssertionError("Module name is null or empty");
                }
                // The module name should not start with "/modules/" prefix
                if (name.startsWith("/modules/")) {
                    throw new AssertionError("Module name should not start with '/modules/': " + name);
                }
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetModuleNamesThrowsWhenClosed() throws IOException {
        java.nio.file.Path imagePath = java.nio.file.Paths.get(System.getProperty("java.home"), "lib", "modules");

        ImageReader reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
        reader.close();

        try {
            reader.getModuleNames();
            throw new AssertionError("Expected IllegalStateException when calling getModuleNames() on closed ImageReader");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
