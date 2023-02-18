package ch.hpdy.songselector.vorlagen;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooser1 {

    public static void main(String[] args) {

        chooseFileName(FileSystemView.getFileSystemView().getHomeDirectory());

    }

    public static String chooseFileName(File basePath) {

        JFileChooser jfc = new JFileChooser(basePath);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);


        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile.getName();
        }
        return null;
    }
    public static File chooseFile(File path) {

        JFileChooser jfc = new JFileChooser(path);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile;
        }
        return null;
    }
}