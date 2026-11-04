/*
 * @test
 * @summary Tests argument validation and stored values of SSLParameters.setApplicationProtocols
 * @run main SSLParametersWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {

    public static void main(String[] args) {
        testNullProtocolsThrows();
        testNullElementThrows();
        testEmptyElementThrows();
        testValidValuesStored();
        testDefensiveCopy();
        testFailedSetKeepsPreviousValue();
    }

    static void testNullProtocolsThrows() {
        expectIllegalArgument(null);
    }

    static void testNullElementThrows() {
        expectIllegalArgument(new String[] {"h2", null});
    }

    static void testEmptyElementThrows() {
        expectIllegalArgument(new String[] {"h2", ""});
    }

    static void testValidValuesStored() {
        SSLParameters params = new SSLParameters();
        String[] values = {"h2", "http/1.1"};
        params.setApplicationProtocols(values);
        check(Arrays.equals(values, params.getApplicationProtocols()),
              "stored protocols differ from input");
    }

    static void testDefensiveCopy() {
        SSLParameters params = new SSLParameters();
        String[] values = {"h2", "http/1.1"};
        params.setApplicationProtocols(values);
        values[0] = "changed";
        check("h2".equals(params.getApplicationProtocols()[0]),
              "stored protocols were affected by input mutation");
    }

    static void testFailedSetKeepsPreviousValue() {
        SSLParameters params = new SSLParameters();
        params.setApplicationProtocols(new String[] {"h2"});
        try {
            params.setApplicationProtocols(new String[] {"a", ""});
            throw new RuntimeException("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        check(Arrays.equals(new String[] {"h2"}, params.getApplicationProtocols()),
              "previous value changed after rejected input");
    }

    static void expectIllegalArgument(String[] protocols) {
        SSLParameters params = new SSLParameters();
        try {
            params.setApplicationProtocols(protocols);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
