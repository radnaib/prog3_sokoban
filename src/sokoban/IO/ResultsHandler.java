package sokoban.IO;

import sokoban.logic.Result;
import sokoban.logic.Results;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ResultsHandler extends DefaultHandler {
    private static final String xmlHeader =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String dateFormat = "yyyy-MM-dd HH:mm";

    private Results results;
    private ArrayList<ArrayList<Result>> collectionResults;
    private ArrayList<Result> levelResults;
    private int levelNumber;
    private int rank;
    private String data;

    private int moves, pushes, time;
    private String name;
    private LocalDateTime date;

    private ResultsHandler() {}

    @Override
    public void startElement(String namespaceURI, String sName,
                             String qName, Attributes attrs)
            throws SAXException {
        if (qName.equals("collection_results")) {
            collectionResults = new ArrayList<ArrayList<Result>>();
        }
        else if (qName.equals("level_results")) {
            levelNumber = Integer.parseInt(
                    attrs.getValue("level_number"));
            levelResults = new ArrayList<Result>();
        } else if (!qName.equals("result")) {
            rank = 0;
            data = "";
        }
    }

    @Override
    public void endElement(String namespaceURI, String sName, String qName)
            throws SAXException {
        if (qName.equals("collection_results")) {
            results = new Results(collectionResults);
        } else if (qName.equals("level_results")) {
            collectionResults.add(levelResults);
        } else if (qName.equals("result")) {
            levelResults.add(
                    new Result(rank, name, moves, pushes, time, date));
            rank++;
        } else if (qName.equals("name")) {
            name = data;
        } else if (qName.equals("moves")) {
            moves = Integer.parseInt(data);
        } else if (qName.equals("pushes")) {
            pushes = Integer.parseInt(data);
        } else if (qName.equals("time")) {
            time = Integer.parseInt(data);
        } else if (qName.equals("date")) {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(dateFormat);
            date = LocalDateTime.parse(data, formatter);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        data = new String(ch, start, length);
    }

    public static Results loadResults(File f)
            throws ParserConfigurationException, SAXException, IOException {
        if (!f.exists()) {
            return new Results(new ArrayList<ArrayList<Result>>());
        }

        ResultsHandler rh = new ResultsHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser p = factory.newSAXParser();
        p.parse(f, rh);
        return rh.results;
    }

    public static void println(PrintStream ps, int tab, String str) {
        for (int i = 0; i < tab; i++) {
            ps.print("\t");
        }
        ps.println(str);
    }

    public static void saveResults(FileOutputStream os, Results results) {
        PrintStream ps = new PrintStream(os);
        println(ps, 0, xmlHeader);
        println(ps, 0, "<collection_results>");
        ArrayList<ArrayList<Result>> levelCollResults = results.getResults();
        int nLevels = levelCollResults.size();

        for (int i = 0; i < nLevels; i++) {
            println(ps, 1, "<level_results level_number=\"" + (i+1) + "\">");
            for (Result res : levelCollResults.get(i)) {
                println(ps, 2, "<result>");
                println(ps, 3, "<name>"+res.getName()+"</name>");
                println(ps, 3, "<moves>"+res.getMoveCount()+"</moves>");
                println(ps, 3, "<pushes>"+res.getPushCount()+"</pushes>");
                println(ps, 3, "<time>"+res.getTime()+"</time>");
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(dateFormat);
                println(ps, 3, "<date>"+
                        res.getDate().format(formatter)+"</date>");
                println(ps,2, "</result>");
            }
            println(ps, 1, "</level_results>");
        }
        println(ps, 0, "</collection_results>");
        ps.close();
    }

}
