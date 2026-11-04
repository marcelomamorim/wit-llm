/*
 * @test
 * @summary Verify ImageReader.getResourceBuffer rejects calls after the reader is closed and that a second close is rejected
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        Path modules = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(modules, ByteOrder.nativeOrder());

        reader.close();

        boolean thrown = false;
        try {
            reader.getResourceBuffer((ImageLocation) null);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException from getResourceBuffer on closed reader");
        }

        thrown = false;
        try {
            reader.close();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IOException on second close");
        }
    }
}
