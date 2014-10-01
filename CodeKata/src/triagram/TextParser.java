package triagram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextParser is a class that parses a text file and extracts words from it to
 * populate a Map<String, ArrayList<String>>.
 * 
 * The triagram Map is generated by parsing each word in the text file and using
 * two adjacent words as key and adding the third word following them as value.
 * 
 * @author smajji
 * 
 */
public class TextParser {

	/**
	 * parses a text file to extract words from it and populates a List<String>
	 * with these words.
	 * 
	 * @param fileName
	 *            : location of the text file to be parsed.
	 * @return List<String>
	 * @throws IOException
	 */
	public final List<String> parseFile(String fileName) throws IOException {
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException(
					"Please, provide a valid file name.");
		}
		Scanner fileScan = null;
		List<String> wordList = null;
		Pattern pattern = Pattern.compile("[0-9]|\\p{Punct}+"); // regex to
																// match numbers
																// or
																// punctuation.
		Matcher matcher = pattern.matcher("");
		try {
			File file = new File(fileName);
			if (isValidFile(file)) {
				fileScan = new Scanner(file);
				wordList = new ArrayList<String>();
				while (fileScan.hasNextLine()) {
					String line = fileScan.nextLine().trim();//
					matcher = matcher.reset(line);
					line = matcher.replaceAll(" ");
					String[] words = line.split("\\s+");
					for (String word : words) {
						if (!word.isEmpty()) {
							wordList.add(word);
						}
					}
				}
				// scanner suppresses IOException. So, we have to explicity
				// throw IOException.
				if (fileScan.ioException() != null) {
					throw fileScan.ioException();
				}

			} else {
				throw new IllegalArgumentException("File '" + fileName
						+ "' is not a valid text file.");
			}
		} catch (FileNotFoundException fne) {
			throw new IllegalArgumentException("File '" + fileName
					+ "' does not exist.");
		} finally {
			if (fileScan != null) {
				fileScan.close();
			}
		}
		return wordList;
	}

	/**
	 * This method takes a List<String> of words and iterates it to populate a
	 * Map<String, ArrayList<String>> using the following algorithm.
	 * 
	 * The input
	 * 
	 * while iterating the list on its index, index and index+1 element will be
	 * used as key in the map and index+2 will be added to the list in the value
	 * of the map if present else it will create a new List add the index+2
	 * element to the list and set it as the value for the key.
	 * 
	 * @param wordList
	 *            : List<String> of words. should have atleast 3 elements.
	 * @return Map<String, ArrayList<String>>
	 */
	public final Map<String, ArrayList<String>> populateTriagrams(
			List<String> wordList) {
		if (wordList == null) {
			throw new IllegalArgumentException(
					"Please, provide a valid list that is not null.");
		}
		if (wordList.size() < 3) {
			throw new IllegalArgumentException(
					"Please, provide a valid list that has atleast 3 elements to generate Triagram.");
		}
		Map<String, ArrayList<String>> triagramMap = new HashMap<String, ArrayList<String>>();
		int lastSet = wordList.size() - 3;
		for (int i = 0; i <= lastSet; i++) {
			String key = wordList.get(i) + " " + wordList.get(i + 1);
			String value = wordList.get(i + 2);
			if (triagramMap.containsKey(key)) {
				triagramMap.get(key).add(value);
			} else {
				ArrayList<String> triagramList = new ArrayList<String>();
				triagramList.add(value);
				triagramMap.put(key, triagramList);
			}
		}
		return triagramMap;
	}

	/**
	 * This method validates the input file and returns 'true' if valid else it
	 * will return 'false'
	 * 
	 * @param file
	 *            input file
	 * @return boolean true/false based on the files validity
	 */
	private final boolean isValidFile(File file) {
		if (!file.exists()) {
			return false; // file does not exist.
		} else if (!file.canRead()) {
			return false; // lack permission to read file.
		} else if (file.length() == 0) {
			return false; // empty file.
		} else {
			return true; // valid file.
		}
	}
}
