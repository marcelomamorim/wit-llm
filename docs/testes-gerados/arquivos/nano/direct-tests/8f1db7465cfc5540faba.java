/* @test    @summary Test init(byte[] params) method of AlgorithmParameters for correct behavior and exception handling.    @run main AlgorithmParametersInitTest */

import java.security.AlgorithmParameters;
import java.security.AlgorithmParametersSpi;
import java.security.Provider;
import java.io.IOException;

public class AlgorithmParametersInitTest {

    public static void main(String[] args) throws Exception {
        testUninitialized();
        testAlreadyInitialized();
    }

    private static void testUninitialized() throws Exception {
        AlgorithmParameters ap = new DummyAlgorithmParameters();
        byte[] params = {1, 2, 3};
        ap.init(params);
        assert ap != null : "AlgorithmParameters instance should not be null";
        System.out.println("testUninitialized passed");
    }

    private static void testAlreadyInitialized() throws Exception {
        AlgorithmParameters ap = new DummyAlgorithmParameters();
        byte[] params = {4, 5, 6};
        ap.init(params);
        boolean exceptionThrown = false;
        try {
            ap.init(params);
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("already initialized") : "Expected 'already initialized' message";
        }
        assert exceptionThrown : "Expected IOException to be thrown when re-initializing";
        System.out.println("testAlreadyInitialized passed");
    }

    static class DummyAlgorithmParameters extends AlgorithmParameters {
        protected DummyAlgorithmParameters() {
            super(new DummySpi(), null, "DummyAlgorithm");
        }
    }

    static class DummySpi extends AlgorithmParametersSpi {
        private boolean initialized = false;
        @Override
        public void engineInit(byte[] params) {
            if (initialized) {
                throw new IllegalStateException("already initialized");
            }
            initialized = true;
        }
    }
}
