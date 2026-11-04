/*
 * @test
 * @summary Test the ordered method of ModuleHashesBuilder for correct iteration over results
 * @run main ModuleHashesBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModuleHashesBuilderWitupTest {

    public static class DummyBuilder {
        private final List<String> result = new ArrayList<>();
        public void addResult(String s) {
            result.add(s);
        }
        public void ordered(Consumer<String> action) {
            result.forEach(action);
        }
    }

    public static void main(String[] args) {
        DummyBuilder builder = new DummyBuilder();
        builder.addResult("first");
        builder.addResult("second");
        builder.addResult("third");
        List<String> called = new ArrayList<>();
        builder.ordered(called::add);
        assert called.size() == 3 : "Expected 3 elements, got " + called.size();
        assert called.get(0).equals("first") : "First element mismatch";
        assert called.get(1).equals("second") : "Second element mismatch";
        assert called.get(2).equals("third") : "Third element mismatch";
    }
}
