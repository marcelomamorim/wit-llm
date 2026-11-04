/*
 * @test
 * @summary Checks SecureRandom.reseed(SecureRandomParameters) null rejection and delegation to the SPI
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;
import java.security.SecureRandomSpi;

public class SecureRandomWitupTest {

    static final class RecordingSpi extends SecureRandomSpi {
        private static final long serialVersionUID = 1L;
        int calls;
        SecureRandomParameters last;

        @Override
        protected void engineSetSeed(byte[] seed) {
        }

        @Override
        protected void engineNextBytes(byte[] bytes) {
        }

        @Override
        protected byte[] engineGenerateSeed(int numBytes) {
            return new byte[numBytes];
        }

        @Override
        protected void engineReseed(SecureRandomParameters params) {
            calls++;
            last = params;
        }
    }

    static final class TestRandom extends SecureRandom {
        private static final long serialVersionUID = 1L;

        TestRandom(SecureRandomSpi spi) {
            super(spi, null);
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        SecureRandom defaultRandom = new SecureRandom();
        try {
            defaultRandom.reseed(null);
            fail("expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            if (!"params cannot be null".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }

        RecordingSpi spi = new RecordingSpi();
        TestRandom random = new TestRandom(spi);
        SecureRandomParameters params = new SecureRandomParameters() { };

        random.reseed(params);
        if (spi.calls != 1) {
            fail("expected one delegation to engineReseed, got " + spi.calls);
        }
        if (spi.last != params) {
            fail("engineReseed received a different params instance");
        }

        try {
            random.reseed(null);
            fail("expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            if (!"params cannot be null".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
        if (spi.calls != 1) {
            fail("null params must not reach engineReseed; calls=" + spi.calls);
        }
    }
}
