/*
 * @test
 * @summary Tests CertificateSubjectName.encode(OutputStream) round-trip, null name and IOException propagation
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import sun.security.x509.CertificateSubjectName;

public class CertificateSubjectNameWitupTest {

    static final byte[] EMPTY_NAME = {0x30, 0x00};

    static final byte[] CN_A_NAME = {
        0x30, 0x0C,
        0x31, 0x0A,
        0x30, 0x08,
        0x06, 0x03, 0x55, 0x04, 0x03,
        0x0C, 0x01, 0x41
    };

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] encodeOf(CertificateSubjectName csn) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        csn.encode(bos);
        return bos.toByteArray();
    }

    static void testEmptyNameRoundTrip() throws IOException {
        CertificateSubjectName csn =
            new CertificateSubjectName(new ByteArrayInputStream(EMPTY_NAME));
        byte[] out = encodeOf(csn);
        check(Arrays.equals(EMPTY_NAME, out),
              "empty name round-trip mismatch: " + Arrays.toString(out));
    }

    static void testCnNameRoundTrip() throws IOException {
        CertificateSubjectName csn =
            new CertificateSubjectName(new ByteArrayInputStream(CN_A_NAME));
        byte[] out = encodeOf(csn);
        check(Arrays.equals(CN_A_NAME, out),
              "CN name round-trip mismatch: " + Arrays.toString(out));
    }

    static void testEncodeIsRepeatable() throws IOException {
        CertificateSubjectName csn =
            new CertificateSubjectName(new ByteArrayInputStream(CN_A_NAME));
        byte[] first = encodeOf(csn);
        byte[] second = encodeOf(csn);
        check(Arrays.equals(first, second), "repeated encode differs");
    }

    static void testNullNameThrowsNpe() throws IOException {
        CertificateSubjectName csn = new CertificateSubjectName((sun.security.x509.X500Name) null);
        boolean thrown = false;
        try {
            encodeOf(csn);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "encode of null name should throw NullPointerException");
    }

    static void testWriteFailurePropagates() throws IOException {
        CertificateSubjectName csn =
            new CertificateSubjectName(new ByteArrayInputStream(EMPTY_NAME));
        OutputStream failing = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("forced");
            }
        };
        boolean thrown = false;
        try {
            csn.encode(failing);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "IOException from OutputStream should propagate");
    }

    public static void main(String[] args) throws Exception {
        testEmptyNameRoundTrip();
        testCnNameRoundTrip();
        testEncodeIsRepeatable();
        testNullNameThrowsNpe();
        testWriteFailurePropagates();
    }
}
