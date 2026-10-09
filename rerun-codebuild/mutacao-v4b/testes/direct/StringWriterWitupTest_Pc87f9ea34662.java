/* @test
   @summary Verifies the behavior of StringWriter.write(String, int, int).
   @run main StringWriterWitupTest_Pc87f9ea34662
 */
import java.io.StringWriter;
public class StringWriterWitupTest_Pc87f9ea34662 {
    public static void main(String[] args) {
        StringWriter writer = new StringWriter();
        String testString = "Hello, World!";
        int offset = 7;
        int length = 5;
        writer.write(testString, offset, length);
        String result = writer.toString();
        if (!"World".equals(result)) {
            throw new AssertionError("Expected 'World', but got: " + result);
        }
    }
}
