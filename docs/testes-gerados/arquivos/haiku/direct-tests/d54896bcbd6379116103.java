/*
 * @test
 * @summary DerOutputStream.putOID(null) must fail with NullPointerException and write no bytes
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();
        boolean thrown = false;
        try {
            out.putOID(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null oid");
        }
        if (out.size() != 0) {
            throw new RuntimeException("bytes written despite failure: " + out.size());
        }
    }
}
