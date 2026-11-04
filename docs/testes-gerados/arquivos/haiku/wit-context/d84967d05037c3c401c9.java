/*
 * @test
 * @summary Verify that iterators over the available charsets reject remove() with UnsupportedOperationException
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class CharsetWitupTest {
    public static void main(String[] args) throws Exception {
        Map<String, Charset> map = Charset.availableCharsets();
        if (map.isEmpty()) {
            throw new RuntimeException("availableCharsets() returned no charsets");
        }
        Iterator<String> it = map.keySet().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("no iterator elements");
        }
        it.next();
        try {
            it.remove();
            throw new RuntimeException("remove() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected behavior
        }
    }
}
