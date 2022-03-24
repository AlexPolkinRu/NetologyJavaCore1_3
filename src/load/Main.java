package load;

import save.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// Произвести распаковку архива в папке savegames.
// Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
// Вывести в консоль состояние сохранненой игры.

public class Main {
    public static void main(String[] args) {

        final String SAVE_GAMES_DIR = "Games/savegames/";
        final String ZIP_FILENAME = "zipped_savegames.zip";

        openZip(SAVE_GAMES_DIR + ZIP_FILENAME, SAVE_GAMES_DIR);

    }

    public static void openZip(String inputFile, String outputPath) {

        try (FileInputStream fis = new FileInputStream(inputFile);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry ze;
            String filenameUnzip;

            while ((ze = zis.getNextEntry()) != null) {
                filenameUnzip = ze.getName();

                FileOutputStream fos = new FileOutputStream(outputPath + filenameUnzip);

                int c;
                while ((c = zis.read()) != -1) {
                    fos.write(c);
                }

                fos.flush();
                zis.closeEntry();
                fos.close();

                System.out.println(openProgress(outputPath + filenameUnzip));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String objectFilename) {

        GameProgress gp = null;

        try (FileInputStream fis = new FileInputStream(objectFilename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gp = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return gp;
    }
}
