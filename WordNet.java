package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import java.util.*;

/** Store WordNet graph to read hyponyms.
 * 	@author Ayesha Omarali
 */

public class WordNet {

	/** list of Synsets */
	ArrayList<Synset> synsets = new ArrayList<Synset>();

	Digraph hypoGraph;


    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
	public WordNet(String synsetFilename, String hyponymFilename) {
		/** Puts Synset information into ArrayLists of int IDs and strings */
		In i = new In(synsetFilename);
		String line;
		while((line = i.readLine()) != null) {
			String[] synset = line.split(",");
			int id = Integer.parseInt(synset[0]);
			ArrayList<String> synonyms = new ArrayList<String>();

			String[] words = synset[1].split(" ");
			for (String word : words) {
				synonyms.add(word);
			}


			synsets.add(new Synset(id, synonyms));
		}

		/** Parsing hyponyms and creating the digraph */
		In k = new In(hyponymFilename);
		String line1;
		HashMap<Integer, ArrayList<Integer>> hyponyms = new HashMap<Integer, ArrayList<Integer>>();
		HashSet<Integer> seenValues = new HashSet<Integer>();
		while((line1 = k.readLine()) != null) {
			String[] hyponym = line1.split(",");
			int id = Integer.parseInt(hyponym[0]);
			if (!seenValues.contains(id)) {
				seenValues.add(id);
			}

			ArrayList<Integer> values = new ArrayList<Integer>();
			for (int l = 1; l < hyponym.length; l++) {
				int item = Integer.parseInt(hyponym[l]);
				values.add(item);

				if (!seenValues.contains(item)) {
					seenValues.add(item);
				}
			}
			hyponyms.put(id, values);
		}

		hypoGraph = new Digraph(seenValues.size());
		for (int item : hyponyms.keySet()) {
			ArrayList<Integer> values = hyponyms.get(item);
			for (int val : values) {
				hypoGraph.addEdge(item, val);
			}
		}
	}

	/* Returns true if NOUN is a word in some synset. */
    public boolean isNoun(String noun) {
    	for (Synset synset : synsets) {

    		if (synset.getSynonyms().contains(noun)) {
    			return true;
    		}
    	}
    	return false;
    }

    /* Returns the set of all nouns. */
    public Set<String> nouns() {
    	HashSet<String> nounset = new HashSet<String>();
    	for (Synset synset : synsets) {
    		nounset.addAll(synset.getSynonyms());
    	}
    	return nounset;
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
      * WORD. If WORD belongs to multiple synsets, return all hyponyms of
      * all of these synsets. See http://goo.gl/EGLoys for an example.
      * Do not include hyponyms of synonyms.
      */
    public Set<String> hyponyms(String word) {
    	if (!isNoun(word)) {
    		throw new IllegalArgumentException(word + "is not a noun in database.");
    	}
    	Set<Integer> ids = new HashSet<Integer>();

    	for (Synset s : synsets) {
    		if (s.getSynonyms().contains(word)) {
    			ids.add(s.getId());
    		}
    	}
    	Set<String> result = new HashSet<String>();
    	result.add(word);
    	for (int i : ids) {
    		Synset line = synsets.get(i);
    		for (String noun : line.getSynonyms()) {
    			result.add(noun);
    		}
    	}
    	for (int i : GraphHelper.descendants(hypoGraph, ids)) {
    		Synset line = synsets.get(i);
    		result.addAll(line.getSynonyms());
    	}
    	return result;
    }
}
