/*
 * @test
 * @summary Test sun.net.TransferProtocolClient.getResponseString() behavior
 * @run main sun.net.TransferProtocolClientWitupTest
 */

import sun.net.TransferProtocolClient;
import java.util.Vector;

public class TransferProtocolClientWitupTest {
    public static void main(String[] args) {
        testGetResponseStringReturnsFirstElement();
        testGetResponseStringThrowsIfEmpty();
    }

    static void testGetResponseStringReturnsFirstElement() {
        TransferProtocolClient client = new TransferProtocolClient();
        // Directly manipulate serverResponse vector to add known element
        client.serverResponse.clear();
        client.serverResponse.addElement("TestResponseLine");

        String response = client.getResponseString();
        if (!"TestResponseLine".equals(response)) {
            throw new RuntimeException("Expected 'TestResponseLine' but got: " + response);
        }
    }

    static void testGetResponseStringThrowsIfEmpty() {
        TransferProtocolClient client = new TransferProtocolClient();
        client.serverResponse.clear();

        try {
            client.getResponseString();
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException when serverResponse is empty");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
