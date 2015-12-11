package ngordnet;
import java.util.ArrayList;
import java.util.Iterator;

/** Data structure to represent Synset sets consisting of the 
 *  integer ID and List of synonyms within the given set. 
 *  @author Ayesha
 */

 public class Synset {
 	/** integer ID */
 	private int id;

 	/**set of synonyms */
 	private ArrayList<String> synonymSet;

 	public Synset(int id, ArrayList<String> synonymSet) {
 		this.id = id;
 		this.synonymSet = synonymSet;
 	}

 	/** Returns ID */
 	public int getId() {
 		return this.id;
 	}

 	/** Returns set of synonyms */
 	public ArrayList<String> getSynonyms() {
 		return this.synonymSet;
 	}
 }