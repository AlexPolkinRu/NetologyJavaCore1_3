package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        final String GAME_PROGRESS_PATH = "Games/savegames/";
        final String ZIP_FILENAME = "zipped_savegames.zip";

        final GameProgress[] GAME_PROGRESSES_ARRAY = {
                new GameProgress(90, 80, 3, 100.0),
                new GameProgress(40, 90, 10, 230.0),
                new GameProgress(100, 100, 1, 0.0)
        };

        for (int i = 0; i < GAME_PROGRESSES_ARRAY.length; i++) {
            try (FileOutputStream fos = new FileOutputStream(GAME_PROGRESS_PATH + "save" + (i + 1) + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(GAME_PROGRESSES_ARRAY[i]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try (FileOutputStream fos = new FileOutputStream(GAME_PROGRESS_PATH + ZIP_FILENAME);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File fileToZip : new File(GAME_PROGRESS_PATH).listFiles()) {

                if (fileToZip.isDirectory() || fileToZip.getName().equals(ZIP_FILENAME)) {
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();

                    fis.close();
                    File file = new File(GAME_PROGRESS_PATH + fileToZip.getName());
                    if (!file.delete()) {
                        System.out.println("Ошибка удаления");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
