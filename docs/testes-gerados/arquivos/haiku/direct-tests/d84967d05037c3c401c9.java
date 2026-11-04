/*
 * @test
 * @summary Verify that iterators over available charsets reject remove() with UnsupportedOperationException
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

public class CharsetWitupTest {
    public static void main(String[] args) {
        SortedMap<String, Charset> map = Charset.availableCharsets();

        Iterator<String> keys = map.keySet().iterator();
        if (!keys.hasNext()) {
            throw new RuntimeException("No available charsets");
        }
        keys.next();
        try {
            keys.remove();
            throw new RuntimeException("keySet iterator remove() did not throw");
        } catch (UnsupportedOperationException expected) {
        }

        Iterator<Charset> values = map.values().iterator();
        if (!values.hasNext()) {
            throw new RuntimeException("No available charset values");
        }
        values.next();
        try {
            values.remove();
            throw new RuntimeException("values iterator remove() did not throw");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
