package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stanford.nlp.ie.machinereading.structure.Span;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import kotlinx.html.P;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Parser {

    private String host = "192.0.0.1";
    private String port = "2323";
    private String mystring = "Find the number of car crashes in america in 2019.";


    public Parser() {
    }

    public String parse(String inputLine) {
        Properties props = new Properties();
        props.setProperty("outputFormat", "conll");
        props.setProperty("annotators", "tokenize, ssplit, truecase, pos, lemma, ner");
        props.setProperty("truecase.overwriteText", "true");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation(mystring);

        pipeline.annotate(annotation);



        try {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pipeline.jsonPrint(annotation, new OutputStreamWriter(stream));

                System.out.println(stream.toString());

                JSONParser jsonParser = new JSONParser();
                JSONObject data = (JSONObject) jsonParser.parse(stream.toString());

                JSONObject sentence = (JSONObject) ((JSONArray) data.get("sentences")).get(0);
                JSONArray entityMentions = (JSONArray) sentence.get("entitymentions");
                List<String> regions = new ArrayList<>();
                for (Object o:entityMentions) {
                    JSONObject entityMention = (JSONObject) o;
                    String location = (String) entityMention.get("ner");
                    if (location.equals("COUNTRY")
                    || location.equals("REGION")
                    || location.equals("CITY")) {
                        regions.add((String) entityMention.get("text"));
                    }
                }

                JSONArray tokens = (JSONArray) sentence.get("tokens");
                List<String> nouns = new ArrayList<>();
                for (Object o:tokens) {
                    JSONObject word = (JSONObject) o;
                    String POS = (String) word.get("pos");
                    if (POS.equals("NN")
                    || POS.equals("NNS")
                    || POS.equals("NNP")
                    || POS.equals("NNPS")) {
                        String actualWord = (String) word.get("truecaseText");
                        if (actualWord.toUpperCase().equals("EUROPE")
                        || actualWord.toUpperCase().equals("ASIA")
                        || actualWord.toUpperCase().equals("AMERICA")
                        || actualWord.toUpperCase().equals("OCEANIA")
                        || actualWord.toUpperCase().equals("AFRICA")) {
                            if (!regions.contains(actualWord)) {
                                regions.add(actualWord);
                            }
                        } else {
                            nouns.add((String) word.get("truecaseText"));
                        }
                    }
                }

                nouns.removeAll(regions);

                System.out.println(regions.toString());
                System.out.println(nouns.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }





        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
