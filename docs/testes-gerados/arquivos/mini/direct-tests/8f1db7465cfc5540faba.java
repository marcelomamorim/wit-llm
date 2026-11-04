/*
 * @test
 * @summary Test java.security.AlgorithmParameters.init(byte[]) method behavior
 * @run main AlgorithmParametersWitupTest
 */

import java.security.AlgorithmParameters;
import java.security.Provider;
import java.io.IOException;

public class AlgorithmParametersWitupTest {

    // Minimal AlgorithmParametersSpi stub to test init(byte[])
    static class TestAlgorithmParametersSpi extends AlgorithmParametersSpi {
        boolean initCalled = false;
        byte[] receivedParams = null;

        @Override
        protected void engineInit(byte[] params) throws IOException {
            if (params == null) {
                throw new IOException("params is null");
            }
            initCalled = true;
            receivedParams = params.clone();
        }

        @Override
        protected void engineInit(java.security.spec.AlgorithmParameterSpec paramSpec) {
            // Not used in this test
        }

        @Override
        protected <T extends java.security.spec.AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramSpec) {
            return null;
        }

        @Override
        protected byte[] engineGetEncoded() {
            return new byte[0];
        }

        @Override
        protected byte[] engineGetEncoded(String format) {
            return new byte[0];
        }

        @Override
        protected String engineToString() {
            return "TestAlgorithmParametersSpi";
        }
    }

    public static void main(String[] args) throws Exception {
        TestAlgorithmParametersSpi spi = new TestAlgorithmParametersSpi();
        Provider provider = new Provider("TestProvider", 1.0, "Test Provider") {};
        AlgorithmParameters ap = new AlgorithmParameters(spi, provider, "TestAlgorithm");

        // Test normal init with non-null byte array
        byte[] params = {1, 2, 3};
        ap.init(params);
        if (!spi.initCalled) {
            throw new RuntimeException("engineInit(byte[]) was not called");
        }
        if (spi.receivedParams == null || spi.receivedParams.length != params.length) {
            throw new RuntimeException("engineInit(byte[]) did not receive correct params");
        }
        for (int i = 0; i < params.length; i++) {
            if (spi.receivedParams[i] != params[i]) {
                throw new RuntimeException("engineInit(byte[]) received incorrect params content");
            }
        }

        // Test that calling init again throws IOException
        boolean threw = false;
        try {
            ap.init(params);
        } catch (IOException e) {
            if (!"already initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown on second init call");
        }

        // Test that init with null byte array throws IOException from SPI
        TestAlgorithmParametersSpi spi2 = new TestAlgorithmParametersSpi();
        AlgorithmParameters ap2 = new AlgorithmParameters(spi2, provider, "TestAlgorithm");
        threw = false;
        try {
            ap2.init(null);
        } catch (IOException e) {
            if (!"params is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message for null params: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown on init with null params");
        }
    }
}
