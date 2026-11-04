/*
 * @test
 * @summary Test that getChildren() throws IllegalArgumentException with correct message
 * @run main ImageReaderWitupTest
 */

import java.util.List;

public class ImageReaderWitupTest {

    static class DummyImageReader extends jdk.internal.jimage.ImageReader {
        DummyImageReader() {
            super(null);
        }

        @Override
        public List<jdk.internal.jimage.Node> getChildren() {
            throw new IllegalArgumentException("not a directory: " + getNameString());
        }

        String getNameString() {
            return "dummyNode";
        }
    }

    public static void main(String[] args) {
        DummyImageReader reader = new DummyImageReader();
        try {
            reader.getChildren();
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.equals("not a directory: dummyNode")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
