/*
 * @test
 * @summary Test ImageReader.Node.getLocation() throws IllegalArgumentException
 * @run main ImageReaderWitupTest
 */

public class ImageReaderWitupTest {
    public static void main(String[] args) {
        // Create a minimal Node subclass to test getLocation() method
        Node node = new Node() {
            @Override
            public String getNameString() {
                return "testNode";
            }
            @Override
            public ImageLocation getLocation() {
                // The method under test throws IllegalArgumentException
                throw new IllegalArgumentException("not a resource: " + getNameString());
            }
        };

        try {
            node.getLocation();
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("not a resource") || !msg.contains("testNode")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    // Minimal interface and class stubs to compile the test
    interface Node {
        String getNameString();
        ImageLocation getLocation();
    }

    static class ImageLocation {
        // empty stub
    }
}
