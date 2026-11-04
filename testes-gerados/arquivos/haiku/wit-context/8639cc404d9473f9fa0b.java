/*
 * @test
 * @summary Checks AlgorithmParameters.init(byte[], String) rejects re-initialization and keeps the object retryable after a delegate failure
 * @run main AlgorithmParametersWitupTest
 */

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class AlgorithmParametersWitupTest {

    static class RecordingSpi extends AlgorithmParametersSpi {
        int calls;
        boolean failFirst;
        byte[] lastParams;
        String lastFormat;

        @Override
        protected void engineInit(AlgorithmParameterSpec paramSpec)
                throws InvalidParameterSpecException {
            throw new InvalidParameterSpecException("unsupported");
        }

        @Override
        protected void engineInit(byte[] params) throws IOException {
        }

        @Override
        protected void engineInit(byte[] params, String format) throws IOException {
            calls++;
            lastParams = params;
            lastFormat = format;
            if (failFirst && calls == 1) {
                throw new IOException("delegate failure");
            }
        }

        @Override
        protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramSpec)
                throws InvalidParameterSpecException {
            throw new InvalidParameterSpecException("unsupported");
        }

        @Override
        protected byte[] engineGetEncoded() throws IOException {
            return new byte[0];
        }

        @Override
        protected byte[] engineGetEncoded(String format) throws IOException {
            return new byte[0];
        }

        @Override
        protected String engineToString() {
            return "recording";
        }
    }

    static class TestParams extends AlgorithmParameters {
        TestParams(AlgorithmParametersSpi spi) {
            super(spi, null, "Test");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFirstInitDelegates() throws IOException {
        RecordingSpi spi = new RecordingSpi();
        TestParams p = new TestParams(spi);
        byte[] data = new byte[] {1, 2, 3};
        p.init(data, "ASN.1");
        check(spi.calls == 1, "delegate should be called once");
        check("ASN.1".equals(spi.lastFormat), "format not forwarded");
        check(spi.lastParams == data, "params not forwarded");
        check("Test".equals(p.getAlgorithm()), "algorithm changed");
    }

    static void testSecondInitRejected() throws IOException {
        RecordingSpi spi = new RecordingSpi();
        TestParams p = new TestParams(spi);
        p.init(new byte[] {1}, "ASN.1");
        boolean thrown = false;
        try {
            p.init(new byte[] {2}, "ASN.1");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "second init must throw IOException");
        check(spi.calls == 1, "delegate must not be called on rejected init");
    }

    static void testFailedDelegateKeepsObjectUninitialized() throws IOException {
        RecordingSpi spi = new RecordingSpi();
        spi.failFirst = true;
        TestParams p = new TestParams(spi);
        boolean thrown = false;
        try {
            p.init(new byte[] {1}, "ASN.1");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delegate IOException must propagate");
        p.init(new byte[] {3}, "ASN.1");
        check(spi.calls == 2, "init should be retryable after delegate failure");
    }

    public static void main(String[] args) throws Exception {
        testFirstInitDelegates();
        testSecondInitRejected();
        testFailedDelegateKeepsObjectUninitialized();
    }
}
