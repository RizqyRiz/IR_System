package ir.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;
import java.io.IOException;
import java.util.*;

/**
 * <p>Titre : Paice Husk Stemmer</p>
 * <p>Traduit de la version PHP de Alexis Ulrich (ulrich_alexis@yahoo.com) par Tito Colonna (tcolonna@ecoco.fr)</p>
 * <p>Cette version ne dérive que les mots simples sans les comparer ā une liste de stopwords.</p>
 *
 * <p>Title: Paice Husk Stemmer</p>
 * <p>Translated by Tito Colonna (tcolonna@ecoco.fr) from the PHP version by Alexis Ulrich (ulrich_alexis@yahoo.com)</p>
 * <p>That version does not handle compound words and does not use a stopwords list.</p>
 *
 * @version 1.0
 */
public class PaiceHuskStemmer {
    public PaiceHuskStemmer() {
    }

    static Vector punctuation = null;
    static Vector stopwords = null;
    //static String rule_pattern = "^([a-zA-Z]*)(\\\\*){0,1}(\\\\d)([a-zA-Z]*)([.|>])";
    static String rule_pattern = "^([a-zA-Z]*)(\\*){0,1}(\\d)([a-zA-Z]*)([.|>])";


//    public static String stemPaiceHusk(String form) {
//        boolean stem_found = false;
//        int rule_number = 0;
//        String rule;
//        while (true) {
//            rule_number = getFirstRule(form, rule_number);
//            if (rule_number == -1) {
//                break;
//            }
//            rule = stemrules_array[rule_number];
//            Pattern pattern = Pattern.compile(rule_pattern);
//            Matcher matcher = pattern.matcher(rule);
//            if (matcher.matches()) {
//                if (matcher.group(2) == null || !(matcher.group(2)).equals("*")) {
//                    form = form.substring(0, Integer.parseInt(matcher.group(3))) + matcher.group(4);
//                    if (checkAcceptability(form)) {
//                        if (matcher.group(5).equals(".")) break;
//                    } else {
//                        rule_number++;
//                    }
//                } else {
//                    rule_number++;
//                }
//            } else {
//                rule_number++;
//            }
//        }
//        return form;
//    }
    
    public static String stemPaiceHusk(String form) {
        int rule_number = 0;
        String rule;
        System.out.println("Original word: " + form);
        while (true) {
            rule_number = getFirstRule(form, rule_number);
            System.out.println("Applying rule number: " + rule_number);
            if (rule_number == -1) {
                break;
            }
            rule = stemrules_array[rule_number];
            System.out.println("Applying rule: " + rule);
            String[] parts = rule.split(">");
            String suffixToRemove = parts[0];
            String suffixToAdd = parts.length > 1 ? parts[1] : ""; // Handle cases where rule doesn't contain '>'
            if (form.endsWith(suffixToRemove)) {
                String newForm = form.substring(0, form.length() - suffixToRemove.length()) + suffixToAdd;
                System.out.println("New form after applying rule: " + newForm);
                if (checkAcceptability(newForm)) {
                    form = newForm;
                    System.out.println("Form accepted: " + form);
                    if (parts.length == 1) break; // if the rule doesn't contain '>', no need to continue
                } else {
                    rule_number++;
                    System.out.println("Form not accepted. Incrementing rule number.");
                }
            } else {
                rule_number++;
                System.out.println("Skipping rule due to mismatch.");
            }
        }
        return form;
    }



//    private static int getFirstRule(String form, int startAt) {
//        String new_rule = "";
//        String rule = "";
//        for (int count = startAt; count < rulesCount; count++) {
//            rule = stemrules_array[count];
//            new_rule = regexpMatchAndReplace(rule, rule_pattern, "$1");
//            if (form.startsWith(new_rule)) return count;
//        }
//        return -1;
//    }

    private static int getFirstRule(String form, int startAt) {
        String rule;
        for (int count = startAt; count < rulesCount; count++) {
            rule = stemrules_array[count];
            if (form.endsWith(rule.split(">")[0])) return count;
        }
        return -1;
    }



    private static String regexpMatchAndReplace(String text, String patternStr, String replaceStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replaceStr);
    }

    private static boolean regexpFind(String text, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private static boolean checkAcceptability(String form) {
        if (regexpFind(form, "[aeiou]$")) {
            // if form ends with a vowel, at least two letters must remain
            return (form.length() > 2);
        } else {
            // if form ends with a consonant, at least two letters must remain
            if (form.length() <= 2) return false;
            // and at least one of these must be a vowel or 'y'
            return regexpFind(form, "[aeiouy]");
        }
    }


    static String punctuation_array[] = {".", ",", ";", ":", "!", "?", "'", "`", "\"", "(", ")", "--"};


    static String stemrules_array[] = {
	    "sses>s",
	    "ies>i",
	    "ss>",
	    "s>",
	    "eed>ee",
	    "ed>",
	    "ing>",
	    "at>",
	    "bl>",
	    "iz>",
	    "at>",
	    "iz>",
	    "al>",
	    "ent>",
	    "ous>",
	    "ive>",
	    "ful>",
	    "ize>",
	    "ed>",
	    "ing>",
	    // Step 2
	    "ational>ate",
	    "tional>tion",
	    "enci>ence",
	    "anci>ance",
	    "izer>ize",
	    "bli>ble",
	    "alli>al",
	    "entli>ent",
	    "eli>e",
	    "ousli>ous",
	    "ization>ize",
	    "ation>ate",
	    "ator>ate",
	    "alism>al",
	    "iveness>ive",
	    "fulness>ful",
	    "ousness>ous",
	    // Step 3
	    "icate>ic",
	    "ative>",
	    "alize>al",
	    "iciti>ic",
	    "ical>ic",
	    "ful>",
	    "ness>",
	    // Step 4
	    "al>",
	    "ance>",
	    "ence>",
	    "er>",
	    "ic>",
	    "able>",
	    "ible>",
	    "ant>",
	    "ement>",
	    "ment>",
	    "ent>",
	    // Step 5a
	    "e>",
	    // Step 5b
	    "l>",
	    "s>"
	};


    static int rulesCount = stemrules_array.length;
    
    
    /**
     * For testing, print the stemmed version of a word
     */
    public static void main(String[] args) throws IOException {  
    	String word = "testing";
    	String stem = stemPaiceHusk(word);
    	System.out.println(stem);
    }

}
