/* @test
   @summary Tests the setApplicationProtocols method of SSLParameters.
   @run main SSLParametersWitupTest_P7910909492c9
 */
import java.util.Objects;
import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P7910909492c9 {
    public static void main(String[] args) {
        testSetApplicationProtocolsWithValidInput();
        testSetApplicationProtocolsWithNull();
        testSetApplicationProtocolsWithEmptyString();
        testSetApplicationProtocolsWithNullElement();
    }

    private static void testSetApplicationProtocolsWithValidInput() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        assert Objects.deepEquals(protocols, params.applicationProtocols) : "Failed: valid input";
    }

    private static void testSetApplicationProtocolsWithNull() {
        SSLParameters params = new SSLParameters();
        boolean exceptionThrown = false;
        try {
            params.setApplicationProtocols(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: null input did not throw";
    }

    private static void testSetApplicationProtocolsWithEmptyString() {
        SSLParameters params = new SSLParameters();
        boolean exceptionThrown = false;
        try {
            params.setApplicationProtocols(new String[] {""});
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: empty string element did not throw";
    }

    private static void testSetApplicationProtocolsWithNullElement() {
        SSLParameters params = new SSLParameters();
        boolean exceptionThrown = false;
        try {
            params.setApplicationProtocols(new String[] {null});
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: null element did not throw";
    }
}
