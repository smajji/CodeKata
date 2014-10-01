package triagramTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import triagram.TextParser;
import triagram.TriagramGenerator;

public class TriagramGeneratorTest {

	TextParser textParser = null;
	TriagramGenerator triagramGenerator = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		textParser = new TextParser();
		triagramGenerator = new TriagramGenerator();
	}

	@Test
	public void testGenerateNullInput() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Please, provide a valid input Map.");
		triagramGenerator.generate(null);
	}

	@Test
	public void testGenerateEmptyInput() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Please, provide a valid input Map.");
		Map<String, ArrayList<String>> emptyMap = new HashMap<String, ArrayList<String>>();
		triagramGenerator.generate(emptyMap);
	}
	
	@Test
	public void testGenerateWithMapNotTriagramAnalzed() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Please, provide a valid Triagram analized map.");
		Map<String, ArrayList<String>> emptyMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> wordList = new ArrayList<String>() {
			{
				add("Not Triagram Analyzed");		
			}
		};
		emptyMap.put("Test", wordList);
		triagramGenerator.generate(emptyMap);
	}

	@Test
	public void testGenerateSimple() {
		try {
			List<String> wordList = textParser
					.parseFile("C:\\Kata\\testfiles\\punctuationTest.txt");
			Map<String, ArrayList<String>> triagramMap = textParser
					.populateTriagrams(wordList);
			String result = triagramGenerator.generate(triagramMap);
			assertTrue(result.endsWith("might"));
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testGenerateWithMapofOneElement() {

		List<String> wordList = new ArrayList<String>() {
			{
				add("One");
				add("Two");
				add("Three");
			}
		};
		Map<String, ArrayList<String>> triagramMap = textParser
				.populateTriagrams(wordList);
		String result = triagramGenerator.generate(triagramMap);
		assertTrue(result.equals("Three"));

	}

	@Test
	public void testGenerateComplex() {
		try {
			List<String> wordList = textParser
					.parseFile("C:\\Kata\\testfiles\\largeFileFromProjectGutenberg.txt");
			Map<String, ArrayList<String>> triagramMap = textParser
					.populateTriagrams(wordList);
			String result = triagramGenerator.generate(triagramMap);
			String[] words = result.split("\\s+");
			String key = words[0] + " " + words[1];
			String value = words[2];

			assertTrue(triagramMap.get(key).contains(value));
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

}
