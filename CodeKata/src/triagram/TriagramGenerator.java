package triagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * TriagramGenerator is a class that helps generate words based on Map<String,
 * ArrayList<String>> it gets as input picking a starting point randomly from
 * the keys in the Map<String, ArrayList<String>> and randomly choses a new word
 * from the list present in the value for the key.
 * 
 * Also, important to note is, given a large Map there is higher chance of
 * getting OutOfMemory error so we need to limit the number of words it
 * generates.
 * 
 * This limit is set to 300 words.
 * 
 * @author smajji
 * 
 */
public class TriagramGenerator {

	private static final int WORD_LIMIT = 300;

	/**
	 * This method takes a Map<String, ArrayList<String>> as input and starts
	 * randomly from one of the keys in the Map<String, ArrayList<String>> and
	 * picks a random element from the list in the value of the map for this key.
	 * 
	 * It then generates the next key by spliting the current key in 2 using
	 * space as a delimiter. it uses the second word and the current element
	 * picked from the list as the new key.
	 * 
	 * This continues untill no key exists in the Map or it reaches the word
	 * limit.
	 * 
	 * @param triagramMap
	 *            Map<String, ArrayList<String>> used to generate the new words.
	 * @return String of words generated.
	 */
	public final String generate(Map<String, ArrayList<String>> triagramMap) {
		if (triagramMap == null || triagramMap.isEmpty()) {
			throw new IllegalArgumentException(
					"Please, provide a valid input Map.");
		}
		StringBuilder txtBuilder = new StringBuilder();
		// get a random key to start with.
		String startKey = getRandomString(new ArrayList<String>(
				triagramMap.keySet()));
		int counter = 0;
		while (triagramMap.containsKey(startKey) && counter < WORD_LIMIT) {
			String value = getRandomString(triagramMap.get(startKey));
			txtBuilder.append(value).append(" ");
			String[] splitArray = startKey.split(" ");
			if(splitArray.length < 2){
				throw new IllegalArgumentException("Please, provide a valid Triagram analized map.");
			}
			startKey = splitArray[1] + " " + value;
			counter++;
		}
		String result = txtBuilder.toString().trim();
		System.out.println(result);
		return result;
	}

	/**
	 * Picks a random element from the input List<String>.
	 * 
	 * @param stringList List<String>
	 * @return String: a random String value taken from the input String list.
	 */
	private String getRandomString(List<String> stringList) {
		Random rand = new Random();
		int rndInt = rand.nextInt(stringList.size());
		String randomKey = stringList.get(rndInt);
		return randomKey;
	}

}
