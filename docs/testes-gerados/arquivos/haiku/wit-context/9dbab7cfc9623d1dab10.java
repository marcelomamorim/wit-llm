/*
 * @test
 * @summary Verify putTruncatedUnalignedBitString rejects a null BitArray without modifying the stream
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        checkNullRejected(new DerOutputStream());
        checkNullRejected(new DerOutputStream(16));
    }

    private static void checkNullRejected(DerOutputStream out) {
        try {
            out.putTruncatedUnalignedBitString(null);
            throw new RuntimeException("NullPointerException expected for null BitArray");
        } catch (NullPointerException expected) {
            // expected: ba.truncate() dereferences the null argument
        } catch (java.io.IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        if (out.size() != 0) {
            throw new RuntimeException("Stream modified after failed call: size=" + out.size());
        }
    }
}
