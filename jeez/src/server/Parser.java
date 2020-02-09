package server;

import edu.stanford.nlp.ie.machinereading.structure.Span;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.simple.Sentence;
import kotlinx.html.P;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Parser {

    private String sentence;
    String host = "192.0.0.1";
    String port = "2323";
    String mystring = "trump is the president of the united states.";


    public Parser() {
        this.sentence = "";
    }

    public String parse(String inputLine) {
        Sentence sentence = new Sentence(inputLine);
        Properties props = new Properties();
        props.setProperty("outputFormat", "conll");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation(mystring);
        Properties trueCaseProps = new Properties();
        trueCaseProps.setProperty("truecase.overwriteText", "true");
        pipeline.addAnnotator(new TokenizerAnnotator());
        pipeline.addAnnotator(new WordsToSentencesAnnotator());
        pipeline.addAnnotator(new TrueCaseAnnotator(trueCaseProps));
        pipeline.annotate(annotation);
        pipeline.prettyPrint(annotation, System.out);


        return "hi";
    }
}
