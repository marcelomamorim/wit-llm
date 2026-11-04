/*
 * @test
 * @summary Tests validation and storage behavior of SSLParameters.setApplicationProtocols
 * @run main SSLParametersWitupTest
 */

import javax.net.ssl.SSLParameters;
import java.util.Arrays;

public class SSLParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArrayRejected();
        testNullElementRejected();
        testEmptyElementRejected();
        testRejectedValueKeepsPreviousValue();
        testValidProtocolsStored();
        testDefensiveCopy();
        testEmptyArrayAccepted();
        System.out.println("PASSED");
    }

    static void expectIllegalArgument(SSLParameters p, String[] value) {
        try {
            p.setApplicationProtocols(value);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void testNullArrayRejected() {
        expectIllegalArgument(new SSLParameters(), null);
    }

    static void testNullElementRejected() {
        expectIllegalArgument(new SSLParameters(), new String[] { "h2", null });
    }

    static void testEmptyElementRejected() {
        expectIllegalArgument(new SSLParameters(), new String[] { "" });
    }

    static void testRejectedValueKeepsPreviousValue() {
        SSLParameters p = new SSLParameters();
        p.setApplicationProtocols(new String[] { "h2", "http/1.1" });
        expectIllegalArgument(p, new String[] { "h2", "" });
        if (!Arrays.equals(p.getApplicationProtocols(),
                new String[] { "h2", "http/1.1" })) {
            throw new RuntimeException("Previous value was modified after rejected input");
        }
    }

    static void testValidProtocolsStored() {
        SSLParameters p = new SSLParameters();
        p.setApplicationProtocols(new String[] { "h2", "http/1.1" });
        if (!Arrays.equals(p.getApplicationProtocols(),
                new String[] { "h2", "http/1.1" })) {
            throw new RuntimeException("Application protocols not stored correctly");
        }
    }

    static void testDefensiveCopy() {
        SSLParameters p = new SSLParameters();
        String[] input = new String[] { "h2" };
        p.setApplicationProtocols(input);
        input[0] = "changed";
        if (!Arrays.equals(p.getApplicationProtocols(), new String[] { "h2" })) {
            throw new RuntimeException("Stored value changed when input array was modified");
        }
    }

    static void testEmptyArrayAccepted() {
        SSLParameters p = new SSLParameters();
        p.setApplicationProtocols(new String[0]);
        if (p.getApplicationProtocols().length != 0) {
            throw new RuntimeException("Expected empty application protocols");
        }
    }
}
