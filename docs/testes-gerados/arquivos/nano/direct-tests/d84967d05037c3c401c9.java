/* @test    @summary Test the remove() method of Charset class for UnsupportedOperationException    @run main CharsetWitupTest */

import java.nio.charset.Charset;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testRemoveMethodThrowsException();
    }

    public static void testRemoveMethodThrowsException() {
        Charset charset = null;
        try {
            // Access the remove() method via reflection, since it's public but throws exception
            charset = Charset.class.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // If instantiation fails, create a dummy instance via anonymous subclass
            charset = new Charset("Dummy", new String[0]) {
                @Override
                public boolean contains(Charset cs) { return false; }
                @Override
                public CharsetDecoder newDecoder() { return null; }
                @Override
                public CharsetEncoder newEncoder() { return null; }
            };
        }
        try {
            charset.remove();
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
