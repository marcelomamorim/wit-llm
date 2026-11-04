/*
 * @test
 * @summary Verify that Collections.emptyList().forEach rejects a null action and does not invoke any action on an empty list
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> empty = Collections.emptyList();
        boolean npe = false;
        try {
            empty.forEach(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("NullPointerException expected for null action");
        }
        int[] calls = {0};
        empty.forEach(s -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("action must not be invoked for an empty list");
        }
    }
}
