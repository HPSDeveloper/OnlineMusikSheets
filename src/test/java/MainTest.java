package ch.hpdy;

// FIXME: 08.02.2019 Provide project as maven project
import org.junit.Test;

/**
 * Created by Hans-Peter Schmid on 30.12.2018.
 */

public class MainTest {
    Main main = new Main();


    @Test
    public void testGetSongBodyAsHtml() {
        System.out.println(
           main.getSongBodyAsHtml("\n{s:ausgang 3} [G]mir. [e2] [e/D] [D11sus]Komm sei ganz [D]nah bei [G]mir.")
        );
    }
    @Test
    public void testHandleOnePhrase1() {
        StringBuilder sb = new StringBuilder();
        main.createDivsForOnePhrase(".", "{t:Here I Am to Worship}\n" +
                " {st:Tim Hughes}\n" +
                " {key:E}\n" +
                "\n" +
                " {s:1}\n" +
                " [E]light of the w[H]orld", sb);
        System.out.println(sb.toString());
    }
    @Test
    public void testHandleOnePhrase2() {
        StringBuilder sb = new StringBuilder();
        main.createDivsForOnePhrase(".", "{s:chorus}Wir sind hier", sb);
        System.out.println(sb.toString());
    }
    @Test
    public void testHandleOnePhrase3() {
        StringBuilder sb = new StringBuilder();
        main.createDivsForOnePhrase("A", "Wir sind hier{s:chorus}", sb);
        System.out.println(sb.toString());
    }

    @Test
    public void testHandleOnePhrase4() {
        StringBuilder sb = new StringBuilder();
        main.createDivsForOnePhrase("H", "Wir sind hier{sx:chorus}", sb);
        System.out.println(sb.toString());
    }
}
