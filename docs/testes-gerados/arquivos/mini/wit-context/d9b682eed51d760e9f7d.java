/*
 * @test
 * @summary Test sun.net.TransferProtocolClient.getResponseString() for expected behavior and exception
 * @run main TransferProtocolClientWitupTest
 */

import sun.net.TransferProtocolClient;

public class TransferProtocolClientWitupTest {
    public static void main(String[] args) throws Exception {
        testGetResponseStringReturnsFirstElement();
        testGetResponseStringThrowsWhenEmpty();
    }

    private static void testGetResponseStringReturnsFirstElement() throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        // Add a response string to serverResponse vector via reflection is not allowed,
        // so we simulate by calling readServerResponse() which clears and fills serverResponse.
        // But since we cannot simulate serverInput easily, we test only getResponseString() after manual setup.
        // Instead, we test that getResponseString() returns the first element if present.

        // We cannot add elements directly to serverResponse (protected field) or simulate serverInput,
        // so we test the behavior by subclassing to add an element.

        TransferProtocolClient clientWithResponse = new TransferProtocolClient() {
            {
                serverResponse.add("Test response line");
            }
        };

        String response = clientWithResponse.getResponseString();
        if (!"Test response line".equals(response)) {
            throw new AssertionError("Expected 'Test response line' but got: " + response);
        }
    }

    private static void testGetResponseStringThrowsWhenEmpty() throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        // serverResponse is empty by default
        // getResponseString() calls serverResponse.elementAt(0) which should throw ArrayIndexOutOfBoundsException
        try {
            client.getResponseString();
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException when serverResponse is empty");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected exception
        }
    }
}
