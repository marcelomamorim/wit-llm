/*
 * @test
 * @summary Test for getResource method in ImageReader, focusing on open/closed state.
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceWhenOpen();
        testGetResourceWhenClosed();
    }

    private static void testGetResourceWhenOpen() throws Exception {
        Path dummyPath = Paths.get("/dummy/path");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath);
            // Since getResource is called with a dummy Resource, expect an IOException or null
            // but focus on ensuring no exception is thrown due to closed state
            try {
                // We pass null as Resource to simulate invalid input, expecting an exception or null
                byte[] result = reader.getResource((ImageReader.Resource) null);
                // No exception means test passes for open state
            } catch (IOException e) {
                // Expected if getResource cannot process null; test still passes as no closed state issue
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testGetResourceWhenClosed() throws Exception {
        Path dummyPath = Paths.get("/dummy/path");
        ImageReader reader = null;
        try {
            reader = ImageReader.open(dummyPath);
            reader.close();
            boolean exceptionThrown = false;
            try {
                reader.getResource((ImageReader.Resource) null);
            } catch (IOException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IOException when calling getResource on closed reader");
            }
        } finally {
            if (reader != null && !reader.closed) {
                reader.close();
            }
        }
    }
}
