package install;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static StringBuilder bufferLog = new StringBuilder();

    public static void main(String[] args) {

        // В папке Games создайте несколько директорий: src, res, savegames, temp.
        // В каталоге src создайте две директории: main, test.
        // В подкаталоге main создайте два файла: Main.java, Utils.java.
        // В каталоге res создайте три директории: drawables, vectors, icons.
        // В директории temp создайте файл temp.txt.

        final String LOG_FILENAME = "Games/temp/temp.txt";

        final List<String> MAKE_DIR_LIST = new ArrayList<>(Arrays.asList(
                "Games/savegames",
                "Games/temp",
                "Games/src/main",
                "Games/src/test",
                "Games/res/drawables",
                "Games/res/vectors",
                "Games/res/icons"
        ));

        for (String newDir : MAKE_DIR_LIST) {
            File dir = new File(newDir);

            if (dir.mkdirs())
                log("Создана директория: " + newDir);
            else
                log("Ошибка создания директории: " + newDir);
        }

        final List<String> MAKE_FILE_LIST = new ArrayList<>(Arrays.asList(
                "Games/src/main/Main.java",
                "Games/src/main/Utils.java",
                "Games/temp/temp.txt"
        ));

        for (String newFile : MAKE_FILE_LIST) {
            try {
                File file = new File(newFile);
                if (file.createNewFile())
                    log("Создан файл: " + newFile);
                else
                    log("Ошибка создания файла: " + newFile);
            } catch (Exception e) {
                log("Ошибка: " + e.getMessage() + " " + newFile);
            }
        }

        saveLogToFile(LOG_FILENAME);
    }

    public static void log(String message) {
        System.out.println(message);
        bufferLog.append(message).append("\n");
    }

    public static void saveLogToFile(String logFile) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(bufferLog.toString());
        } catch (Exception e) {
            System.out.println("Ошибка обращения к файлу: " + e.getMessage());
        }
    }
}