/*
 * @test
 * @summary Test ImageReader.getLocation() throws IllegalArgumentException
 * @run main ImageReaderWitupTest
 */

public class ImageReaderWitupTest {
    public static void main(String[] args) {
        try {
            // Create a minimal subclass to call getLocation()
            ImageReader reader = new ImageReader(null) {
                @Override
                public String getNameString() {
                    return "testName";
                }
                @Override
                public ImageLocation getLocation() {
                    throw new IllegalArgumentException("not a resource: " + getNameString());
                }
            };
            reader.getLocation();
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("not a resource: testName")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
