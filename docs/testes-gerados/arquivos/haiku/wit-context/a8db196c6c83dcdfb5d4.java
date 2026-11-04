/*
 * @test
 * @summary Verifies that ImageReader.getResource(ImageLocation) rejects calls made after the reader is closed
 * @modules java.base/jdk.internal.jimage
 * @run main ImageReaderWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        Path image = Paths.get(System.getProperty("java.home"), "lib", "modules");
        ImageReader reader = ImageReader.open(image);
        reader.close();

        Class<?> locClass = Class.forName("jdk.internal.jimage.ImageLocation");
        Method m = ImageReader.class.getMethod("getResource", locClass);
        try {
            m.invoke(reader, new Object[] { null });
            throw new AssertionError("expected IllegalStateException for closed reader");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalStateException)) {
                throw new AssertionError("unexpected cause", cause);
            }
            if (!"image file closed".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }
    }
}
