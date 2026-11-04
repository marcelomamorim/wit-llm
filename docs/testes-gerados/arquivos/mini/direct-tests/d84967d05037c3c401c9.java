/*
 * @test
 * @summary Test Charset.remove() throws UnsupportedOperationException
 * @run main CharsetWitupTest
 */

public class CharsetWitupTest {
    public static void main(String[] args) {
        java.nio.charset.Charset cs = new java.nio.charset.Charset("TestCharset", new String[0]) {
            @Override
            public boolean contains(java.nio.charset.Charset cs) {
                return false;
            }
            @Override
            public java.nio.charset.CharsetDecoder newDecoder() {
                throw new UnsupportedOperationException();
            }
            @Override
            public java.nio.charset.CharsetEncoder newEncoder() {
                throw new UnsupportedOperationException();
            }
        };

        try {
            cs.remove();
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
