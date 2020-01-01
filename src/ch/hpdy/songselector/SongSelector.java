package ch.hpdy.songselector;

import ch.hpdy.TimeUtil;
import ch.hpdy.songselector.vorlagen.FileChooser1;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SongSelector extends JFrame {
    File basePathFile = null;
    List<String> chosenFiles = new ArrayList<>();
    JTextArea area=new JTextArea();

    public SongSelector() throws URISyntaxException {
        basePathFile = getBasePathFile();
        JButton fileChooserButton = new JButton("chose Songs");
        fileChooserButton.setBounds(10,10,180, 40);//x axis, y axis, width, height

        fileChooserButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                chosenFiles.add(FileChooser1.chooseFileName(getSubFile(basePathFile, "songs")));
                area.setText("");
                chosenFiles.forEach(cf -> area.setText( area.getText() + "\n" + cf));

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(fileChooserButton);//adding button in JFrame

        JTextField songListName = new JTextField(TimeUtil.getCurrentDateTimeFileNameString());
        songListName.setBounds(200,10,180, 40);
        add(songListName);

        JButton saveListButton = new JButton("save list");
        saveListButton.setBounds(400,10,180, 40);
        saveListButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                File saveFilePath = FileChooser1.chooseFile(getSubFile(basePathFile, "selections"));
                File saveFilePath = getSubFile(basePathFile, "selections");
                saveAsTextFile(saveFilePath);
                saveAsHtmlFile(saveFilePath);
            }

            private void saveAsTextFile(File saveFilePath) {
                File saveFile = new  File(saveFilePath, songListName.getText() + ".txt");
                BufferedWriter writer = null;
                try {
                    saveFile.createNewFile();
                    writer = new BufferedWriter(new FileWriter(saveFile));
                    for(String cf : chosenFiles){
                        writer.write(cf);
                        writer.write("\n");
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally{
                    try {
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            private void saveAsHtmlFile(File saveFilePath) {
                File saveFile = new  File(saveFilePath, songListName.getText() + ".html");
                BufferedWriter writer = null;
                try {
                    saveFile.createNewFile();
                    writer = new BufferedWriter(new FileWriter(saveFile));
                    writer.write("<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Songbook</title>\n" +
                            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<table style=\"width:100%\">\n");
                    for(String cf : chosenFiles){
                        writer.write("<tr><td><a href=\"../songs/" + cf + "\">" + cf + "</a></tr>");
                        writer.write("\n");
                    }
                    writer.write("</table>" +
                                    "</body>\n" +
                                   "</html>");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally{
                    try {
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }



            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(saveListButton);


        area.setBounds(10,60, 450,400);


        add(area);

        setSize(600,600);//400 width and 500 height
        setLayout(null);//using no layout managers
        setVisible(true);//making the frame visible
    }

    private File getBasePathFile() throws URISyntaxException {
        File f =  new File(SongSelector.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI());
        return getSubFile(f.getParentFile().getParentFile(), "web");
    }

    private File getSubFile(File f, String subDirName) {
        for (File f1 : f.listFiles()){
            if(f1.isDirectory() && f1.getName().equals(subDirName)){
                return f1;
            }
        }
        return null;
    }


    public static void main(String[] args) throws URISyntaxException {
        SongSelector mainGui = new SongSelector();
    }
}
