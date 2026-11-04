/*
 * @test
 * @summary Verify getResponseString on a freshly created client with no server response throws ArrayIndexOutOfBoundsException
 * @modules java.base/sun.net
 * @run main TransferProtocolClientWitupTest
 */
import sun.net.TransferProtocolClient;

public class TransferProtocolClientWitupTest {
    public static void main(String[] args) throws Exception {
        TransferProtocolClient client = new TransferProtocolClient();
        boolean thrown = false;
        try {
            client.getResponseString();
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException when no server response is present");
        }
    }
}
