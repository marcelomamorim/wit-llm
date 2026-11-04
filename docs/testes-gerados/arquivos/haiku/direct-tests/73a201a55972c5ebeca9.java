/*
 * @test
 * @summary Verifies that ImageHeader.writeTo(ImageStream) rejects a null stream with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main/othervm --add-exports java.base/jdk.internal.jimage=ALL-UNNAMED ImageHeaderWitupTest
 */

import jdk.internal.jimage.ImageHeader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ImageHeaderWitupTest {

    public static void main(String[] args) throws Exception {
        ImageHeader header = new ImageHeader(1, 2, 3, 4);

        Class<?> streamType = Class.forName("jdk.internal.jimage.ImageStream");
        Method writeTo = ImageHeader.class.getMethod("writeTo", streamType);

        try {
            writeTo.invoke(header, (Object) null);
            throw new AssertionError("Expected NullPointerException for null stream");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("Unexpected cause: " + e.getCause());
            }
        }
    }
}
