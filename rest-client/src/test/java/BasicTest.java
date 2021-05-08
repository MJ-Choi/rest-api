import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class BasicTest {

  @Test
  public void lastStringChar() {
    String sample = "https://localhost:1234/";
    System.out.println("sample.charAt(0) = " + sample.charAt(0));
    System.out.println("sample.charAt(-1) = " + sample.charAt(sample.length() - 1));

    char last = sample.charAt(sample.length() - 1);
    System.out.println("last = " + last);
    assertEquals("/", Character.toString(last));
  }

  @Test
  public void removeLastChar() {
    String sample = "https://localhost:1234/";
    String result = sample.substring(0, sample.length() - 1);
    System.out.println("result = " + result);
    assertEquals("https://localhost:1234", result);
  }
}
