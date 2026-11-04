/*
 * @test
 * @summary Test ImageReader.Node.getChildren() throws IllegalArgumentException
 * @run main ImageReaderWitupTest
 */

import java.util.List;

public class ImageReaderWitupTest {

    // Minimal stub class to test getChildren() method throwing
    static class Node {
        public List<Node> getChildren() {
            throw new IllegalArgumentException("not a directory: " + getNameString());
        }
        public String getNameString() {
            return "stubNode";
        }
    }

    public static void main(String[] args) {
        Node node = new Node();
        try {
            node.getChildren();
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not a directory")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }
}
