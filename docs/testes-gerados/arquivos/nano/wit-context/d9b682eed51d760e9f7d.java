/*
 * @test
 * @summary Test for getResponseString method in TransferProtocolClient
 * @run main TransferProtocolClientWitupTest
 */

import sun.net.TransferProtocolClient;
import java.io.*;

public class TransferProtocolClientWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResponseStringNormal();
        testGetResponseStringEmpty();
        testGetResponseStringOutOfBounds();
    }

    static void testGetResponseStringNormal() throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        // Simulate serverResponse with at least one element
        client.serverResponse = new java.util.Vector<>();
        client.serverResponse.add("200 OK");
        String response = client.getResponseString();
        if (!"200 OK".equals(response)) {
            throw new AssertionError("Expected '200 OK', got: " + response);
        }
    }

    static void testGetResponseStringEmpty() throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        // Simulate empty serverResponse
        client.serverResponse = new java.util.Vector<>();
        try {
            client.getResponseString();
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testGetResponseStringOutOfBounds() throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        // Simulate serverResponse with one element
        client.serverResponse = new java.util.Vector<>();
        client.serverResponse.add("Some response");
        // Remove the element to cause out of bounds
        client.serverResponse.remove(0);
        try {
            client.getResponseString();
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
