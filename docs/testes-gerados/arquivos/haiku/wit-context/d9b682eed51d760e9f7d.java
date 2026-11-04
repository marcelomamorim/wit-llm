/*
 * @test
 * @summary Verifies that getResponseString() throws ArrayIndexOutOfBoundsException when no server response has been stored
 * @modules java.base/sun.net
 * @run main TransferProtocolClientWitupTest
 */
import sun.net.TransferProtocolClient;

public class TransferProtocolClientWitupTest {

    public static void main(String[] args) throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        checkThrowsOnFreshClient(client);
        checkThrowsRepeatedly(client);
    }

    private static void checkThrowsOnFreshClient(TransferProtocolClient client) {
        boolean thrown = false;
        try {
            client.getResponseString();
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException when no response is stored");
        }
    }

    private static void checkThrowsRepeatedly(TransferProtocolClient client) {
        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                client.getResponseString();
            } catch (ArrayIndexOutOfBoundsException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected ArrayIndexOutOfBoundsException on repeated call " + i);
            }
        }
    }
}
