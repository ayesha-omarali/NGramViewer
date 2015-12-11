package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.*;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author ayesha
 */

public class NgordnetUI {
        public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");


        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                + wordFile + ", " + countFile + ", " + synsetFile
                + ", and " + hyponymFile + ".");


        int sDate = 0, endDate = 2015;
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        YearlyRecordProcessor yrp = new WordLengthProcessor();
        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            int length = tokens.length;
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            try {
                switch (command) {
                    case "quit":
                        return;
                    case "help":
                        String helpStr = new In("./ngordnet/help.txt").readAll();
                        System.out.println(helpStr);
                        break;
                    case "range":
                        if (sDate < endDate && length== 2) {
                            sDate = Integer.parseInt(tokens[0]);
                            endDate = Integer.parseInt(tokens[1]);
                            System.out.println("Start date:" + sDate + "\nEnd date:" + endDate);
                        }
                        break;
                    case "count":
                        if (length== 2) {
                            int year = Integer.parseInt(tokens[1]);
                            YearlyRecord yr = ngm.getRecord(year);
                            System.out.println(yr.count(tokens[0]) + "\n");
                        }
                        break;
                    case "hyponyms":
                        if (length== 1) {
                            System.out.println(wn.hyponyms(tokens[0]));
                        }
                        break;
                    case "history":
                        Plotter.plotAllWords(ngm, tokens, sDate, endDate);
                        break;
                    case "hypohist":
                        Plotter.plotCategoryWeights(ngm, wn, tokens, sDate,
                                endDate);
                        break;
                    case "zipf":
                        if (length== 1) {
                            Plotter.plotZipfsLaw(ngm,
                                    Integer.parseInt(tokens[0]));
                        }
                        break;
                    case "wordlength":
                        if (length== 0) {
                            Plotter.plotProcessedHistory(ngm, sDate, endDate, yrp);
                        }
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number.");
            } catch (NullPointerException npe) {
                System.out.println("Invalid input.");
            } catch (ArrayIndexOutOfBoundsException aiob) {
                System.out.println("Invalid input length");
            }
        }
    }
}
