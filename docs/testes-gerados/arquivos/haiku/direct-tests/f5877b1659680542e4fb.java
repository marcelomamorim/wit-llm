/*
 * @test
 * @summary Verify Collections.emptyList() replaceAll behavior: no-op for non-null operator, NullPointerException for null operator
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = Collections.emptyList();

        list.replaceAll(s -> s + "x");
        if (!list.isEmpty()) {
            throw new RuntimeException("emptyList must remain empty after replaceAll");
        }

        boolean npeThrown = false;
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("replaceAll(null) must throw NullPointerException");
        }
    }
}
