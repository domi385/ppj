import java.util.regex.Matcher;

public class RegexTest {

	public static void main(String[] args) {
		String input = new String("#|\nhaha");
		System.out.println(input.matches("^(#\\|)(.|\\s)*$"));

	}

}
