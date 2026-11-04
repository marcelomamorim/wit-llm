/*
 * @test
 * @summary Verifies SecureRandom.reseed(SecureRandomParameters) rejects null and forwards non-null parameters to the SPI
 * @run main SecureRandomWitupTest
 */

import java.security.*;

public class SecureRandomWitupTest {

    static final class RecordingSpi extends SecureRandomSpi {
        private static final long serialVersionUID = 1L;
        int reseedCalls;
        SecureRandomParameters lastParams;

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
            reseedCalls++;
            lastParams = params;
        }
    }

    static final class TestRandom extends SecureRandom {
        private static final long serialVersionUID = 1L;

        TestRandom(SecureRandomSpi spi) {
            super(spi, null);
        }
    }

    public static void main(String[] args) throws Exception {
        RecordingSpi spi = new RecordingSpi();
        TestRandom random = new TestRandom(spi);

        boolean rejected = false;
        try {
            random.reseed(null);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        if (!rejected) {
            throw new RuntimeException("reseed(null) must throw IllegalArgumentException");
        }
        if (spi.reseedCalls != 0) {
            throw new RuntimeException("SPI must not be reseeded when params is null");
        }

        SecureRandomParameters first = new SecureRandomParameters() {};
        random.reseed(first);
        if (spi.reseedCalls != 1) {
            throw new RuntimeException("Expected 1 reseed call, got " + spi.reseedCalls);
        }
        if (spi.lastParams != first) {
            throw new RuntimeException("SPI received different parameters object");
        }

        SecureRandomParameters second = new SecureRandomParameters() {};
        random.reseed(second);
        if (spi.reseedCalls != 2) {
            throw new RuntimeException("Expected 2 reseed calls, got " + spi.reseedCalls);
        }
        if (spi.lastParams != second) {
            throw new RuntimeException("SPI did not receive the latest parameters object");
        }
    }
}
