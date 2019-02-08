package ch.hpdy;

import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            if(!check(args)) return;
            (new Main()).handleFile(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean check(String[] args) {
        if(args[0] == null || args[0].length() <= 0){
            System.out.println("ERROR: You must specify a directory path as first argument of the call");
            return false;
        }
        if(!(new File(args[0])).exists()){
            System.out.println("ERROR: The given input path does not exist");
            return false;
        }
        return true;
    }

    private void handleFile(String[] args) throws IOException {
        // FIXME: 23.12.2018 Lese directory von projekt-relativem Pfad
        File folder = new File(args[0]);

        String htmlTemplate = getHtmlTemplate();

        for (File choProSong : listFilesForFolder(folder)) {
            String choproFile = readFileToString(choProSong.getPath(), Charset.forName("UTF8"));
            String htmlSongBody = getSongBodyAsHtml(choproFile);


            String htmlFileContent = htmlTemplate.replaceAll("<div id=\"songBody\"/>", htmlSongBody);
            htmlFileContent = htmlFileContent.replaceAll("<div id=\"headings\"/>", getHeadersAsHtml(choproFile));
            htmlFileContent = htmlFileContent.replaceAll("<button id=\"transpose\"/>", getKeyAsHtml(choproFile));

            writeHtmlFile(htmlFileContent, choProSong.getName().replaceAll("\\.chopro.example|\\.chopro", ".html"));
        }

    }

    private void writeHtmlFile(String htmlFileContent, String fileName) {
        try {
            Files.write(Paths.get("web/" + fileName), htmlFileContent.getBytes());
        } catch (IOException e) {
            System.out.println("Could not write file " + fileName + ". Exception is: " +  e.getMessage());
        }
    }

    private String getHtmlTemplate() throws IOException {
        File templateFile = new File("C:\\Users\\Hans-Peter Schmid\\Documents\\Ausbildung\\SongBook\\out\\production\\SongBook\\htmlTemplate\\template.html");
        return readFileToString(templateFile.getPath(), Charset.forName("UTF8"));
    }

    private String getHeadersAsHtml(String choProSong) {
//        choProSong = "#Ein Lied\n{t:Halleluja}\nThey[G]say there was a [e]secret chord \nthat [G]David played it [e]pleased the Lord";

        StringBuilder sb = new StringBuilder();

        String pattern = "\\{(t|title):(.*)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(choProSong);

        if(m.find()){
            sb.append("<h1>" + m.group(2) + "</h1>");
            System.out.println("Title is: " + m.group(2));
        }

         pattern = "\\{(c|composer):(.*)\\}";
         p = Pattern.compile(pattern);
         m = p.matcher(choProSong);

        if(m.find()){
            sb.append("<h2>" + m.group(2) + "</h2>");
            System.out.println("Compser is: " + m.group(2));
        }

        return sb.toString();
    }

    private String getKeyAsHtml(String choProSong) {
//        choProSong = "#Ein Lied\n{t:Halleluja}\nThey[G]say there was a [e]secret chord \nthat [G]David played it [e]pleased the Lord";

        String pattern = "\\{(key):(.*)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(choProSong);

        if(m.find()){
            String transposeButtons = String.format("<strong id=\"key\" class=\"chord\">%s</strong>"
                    + "<button id=\"transposeUp\">up</button>"
                    + "<button id=\"transposeDown\">down</button>", m.group(2));
            System.out.println("Transpose buttons are: " + transposeButtons);
            return transposeButtons;
        }
        return "";
    }

    protected String getSongBodyAsHtml(String choProSong) {
//        choProSong = "#Ein Lied\n{t:Halleluja}\nThey[G]say there was a [e]secret chord \nthat [G]David played it [e]pleased the Lord";

        StringBuilder sb = new StringBuilder();
        final String chordPattern = "\\[([\\(\\)\\w#/]*)\\]";
        String umlaute = "\\u00C0-\\u017F";
        String pattern = "\\n([" + umlaute + "'\\(\\)\\{\\}:#\\w\\s\\\\]*)(" + chordPattern + ")";


        String begin;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(choProSong);

        if(m.find()){
            begin = m.group(1);
            createDivsForOnePhrase(".", begin, sb);
            int pos = m.end(1);

            String onePhrasePattern = "(([" + umlaute + "\\(\\)'\\{\\}:\\{\\}:\\w\\s,\"\\?\\.!:])*)";
            String oneHarmonyPattern = chordPattern + onePhrasePattern;
            Pattern p3 = Pattern.compile(oneHarmonyPattern);
            Matcher m3 = p3.matcher(choProSong);
            while(m3.find(pos)){
                String chord = m3.group(1);
//                String phrase = m3.group(2).replaceAll("\\n", "");
                String phrase = m3.group(2).replaceAll("(?<!\\n)\\n(?!\\n)", "");
                System.out.println("Harmoniee: " + chord + " / " + phrase);
                int seq = 0;
                final String DELIM = "<<DELIM>>";;
                for(String phrase1 : (phrase + DELIM).split("[\\n\\r]{2,}")) {

                    if(seq > 0) sb.append("</div><div class=\"flex-container\">"); //Create line breaks
                    String usedPhrase = phrase1.replaceAll(DELIM, "");
                    if(usedPhrase.trim().length() > 0 || chord.length() > 0) {
                        createDivsForOnePhrase(chord, phrase1.replaceAll(DELIM, ""), sb);
                        chord = "."; //Chord shall only once be printed.
//                        sb.append(createHTMLDiv(chord, phrase1.replaceAll(DELIM, "")));
                    }
                    seq++;
                }
                pos = m3.end(2);
            }

        }

        System.out.println("The songs DIVs:\n" + sb.toString());
        return sb.toString();
    }


    void createDivsForOnePhrase(String chord, String phrase, StringBuilder sb) {

        Pattern p3 = Pattern.compile("(\\{[\\w]*:[\\w\\s]*})");
        Matcher m3 = p3.matcher(phrase);
        int pos = 0;
        while(m3.find(pos)){
            String before = phrase.substring(pos, m3.start());
            if(before.trim().length() > 0){
                sb.append("\n");
                sb.append(createHTMLDiv(chord, before.trim()));
                chord = "."; //chord is asssigned and should no more be assigned now
            }

            String found = m3.group();
            if(found.startsWith("{s:")){
                sb.append("\n");
                sb.append(createHTMLSectionDiv(chord, found.substring(3,found.length()-1)));
                chord = "."; //chord is asssigned and should no more be assigned now
            }

            String rest = phrase.substring(m3.end());
            System.out.println("Begin: " + before + "//found: " + found + "//rest: " + rest);
            pos = m3.end();
        }
        if(pos < phrase.length() || (chord.length() > 0 && !chord.equals("."))){
            sb.append("\n");
            sb.append(createHTMLDiv(chord, phrase.substring(pos)));
        }

//        sb.append(createHTMLDiv("", phrase));
    }

    private String createHTMLDiv(String chord, String phrase) {
        if(chord == null || chord.length()==0) chord = ".";
        return String.format("<div><div class=\"chord accompaniment\">%s</div>%s</div>", chord, phrase);
    }
    private String createHTMLSectionDiv(String chord, String phrase) {
        if(chord == null || chord.length()==0) chord = ".";
        return String.format("<div><div class=\"chord accompaniment\">%s</div><div class=\"section\">%s</div></div>", chord, phrase);
    }

    static String readFileToString(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static List<File> listFilesForFolder(final File folder) {
        return Arrays.stream(folder.listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());
    }


}
