package netology.saving;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static List<String> savings = new ArrayList<>();

    public static void saveGame(String pathFile, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(pathFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
            savings.add(pathFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZipFile, List<String> files) throws IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZipFile))) {
            for (String s : files) {
                try {
                    FileInputStream fis = new FileInputStream(s);
                    ZipEntry entry = new ZipEntry(new File(s).getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void deleteFiles(List<String> list) {
        for (String s : list) {
            File f = new File(s);
            if (f.delete())
                System.out.println("Файл " + f.getName() + " удалён.");
        }
    }


    public static void main(String[] args) throws IOException {
	// write your code here
        GameProgress gp1 = new GameProgress(100, 1, 1, 4);
        GameProgress gp2 = new GameProgress(97, 2, 3, 8);
        GameProgress gp3 = new GameProgress(100, 3, 5, 12);

        saveGame("E://Games/savegames/save1.dat", gp1);
        saveGame("E://Games/savegames/save2.dat", gp2);
        saveGame("E://Games/savegames/save3.dat", gp3);

        zipFiles("E://Games/savegames/saved.zip", savings);

        deleteFiles(savings);
    }
}
