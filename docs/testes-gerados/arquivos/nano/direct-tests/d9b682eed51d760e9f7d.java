/* @test    @summary Test getResponseString returns the first server response element    @run main TransferProtocolClientWitupTest */

import sun.net.TransferProtocolClient;
import java.io.*;

public class TransferProtocolClientWitupTest {

    public static void main(String[] args) {
        testGetResponseStringReturnsFirstElement();
    }

    public static void testGetResponseStringReturnsFirstElement() {
        TransferProtocolClient client = new TransferProtocolClient();
        // Simulate server response elements
        client.serverResponse = new java.util.Vector<String>();
        client.serverResponse.add("Response1");
        client.serverResponse.add("Response2");
        String response = client.getResponseString();
        if (!"Response1".equals(response)) {
            throw new AssertionError("Expected 'Response1', but got: " + response);
        }
    }
}
