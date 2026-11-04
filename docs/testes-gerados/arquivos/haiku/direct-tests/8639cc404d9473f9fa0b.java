/*
 * @test
 * @summary AlgorithmParameters.init(byte[], String) delegates to the SPI, rejects a second initialization and keeps the initialized flag unset when the SPI fails
 * @run main AlgorithmParametersWitupTest
 */

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class AlgorithmParametersWitupTest {

    static class RecordingSpi extends AlgorithmParametersSpi {
        int initCalls;
        byte[] lastParams;
        String lastFormat;
        boolean failNext;

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
            initCalls++;
            if (failNext) {
                failNext = false;
                throw new IOException("boom");
            }
            lastParams = params;
            lastFormat = format;
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

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testFirstInitDelegates();
        testSecondInitRejected();
        testFailedInitAllowsRetry();
    }

    static void testFirstInitDelegates() throws Exception {
        RecordingSpi spi = new RecordingSpi();
        AlgorithmParameters p = new TestParams(spi);
        check("Test".equals(p.getAlgorithm()), "unexpected algorithm name");
        byte[] data = {1, 2, 3};
        p.init(data, "ASN.1");
        check(spi.initCalls == 1, "SPI should be called once, was " + spi.initCalls);
        check(Arrays.equals(data, spi.lastParams), "params not forwarded");
        check("ASN.1".equals(spi.lastFormat), "format not forwarded");
    }

    static void testSecondInitRejected() throws Exception {
        RecordingSpi spi = new RecordingSpi();
        AlgorithmParameters p = new TestParams(spi);
        p.init(new byte[] {4}, "ASN.1");
        try {
            p.init(new byte[] {5}, "ASN.1");
            throw new RuntimeException("second init should throw IOException");
        } catch (IOException e) {
            check("already initialized".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(spi.initCalls == 1, "SPI must not be called on rejected init");
        check(Arrays.equals(new byte[] {4}, spi.lastParams),
              "original params must be kept");
    }

    static void testFailedInitAllowsRetry() throws Exception {
        RecordingSpi spi = new RecordingSpi();
        spi.failNext = true;
        AlgorithmParameters p = new TestParams(spi);
        try {
            p.init(new byte[] {7}, "ASN.1");
            throw new RuntimeException("SPI failure should propagate");
        } catch (IOException e) {
            check("boom".equals(e.getMessage()), "unexpected SPI message");
        }
        p.init(new byte[] {8}, "ASN.1");
        check(spi.initCalls == 2, "retry should reach SPI, calls=" + spi.initCalls);
        check(Arrays.equals(new byte[] {8}, spi.lastParams),
              "retry params not forwarded");
    }
}
