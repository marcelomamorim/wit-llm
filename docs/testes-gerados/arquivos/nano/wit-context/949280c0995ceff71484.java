/*
 * @test
 * @summary Testa o método forEach de ConcurrentHashMap para verificar comportamento padrão.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testForEachWithValidAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        map.forEach(entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
        String result = sb.toString();
        if (result.contains("a:1") && result.contains("b:2")) {
            System.out.println("PASS: forEach executed correctly, result: " + result);
        } else {
            System.out.println("FAIL: forEach did not process entries as expected, result: " + result);
        }
    }
}
