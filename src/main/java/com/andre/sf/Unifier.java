package com.andre.sf;

import static com.andre.ui.ConsoleColor.ANSI_GREEN;
import static com.andre.ui.ConsoleColor.ANSI_PURPLE;
import static com.andre.ui.ConsoleColor.ANSI_RED;

import com.andre.model.Library;
import com.andre.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Клас обєднувач
 */
public class Unifier {

  /**
   * Зміна маперу
   */
  private ObjectMapper mapper = new ObjectMapper();
  /**
   * Зміна шляху головної папки
   */
  private final String PATH_LIBRARY = "library";
  /**
   * Зміна головної директорія
   */
  private File directory = new File(PATH_LIBRARY);

  /**
   * Масив, який об'єднує дані всіх файлів
   * @param movies приймає масив фільмів
   * @return повертає масив фільмів
   * @throws IOException вийняток
   */
  public List<Movie> unitedFiles(List<Movie> movies) throws IOException {
    checkFile();
    if (!directory.exists()) {
      directory.mkdir();
    }
    File[] listOfFiles = directory.listFiles();
    if (listOfFiles.length <= 0) {
      System.out.println(ANSI_RED +"В вашій кінотеці немає фільмів!!!");
    } else {
      System.out.println(ANSI_PURPLE +"_-_-_ Об'єднуємо файли  _-_-_");
      System.out.println(ANSI_GREEN +"Кількість об'єднаних файлів: "+listOfFiles.length + "\n\nПапки з яких беруться файли:");
      for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isDirectory()) {
          File pathToFile = new File(
              PATH_LIBRARY + "\\" + listOfFiles[i].getName() + "\\movies.json");
          System.out.println(
              "Папка: " + PATH_LIBRARY + "\\" + listOfFiles[i].getName() + "\\movies.json");
          ObjectNode root = (ObjectNode) mapper.readTree(pathToFile);
          ArrayNode array = (ArrayNode) root.get("movies");
          for (int j = 0; j < array.size(); j++) {
            ObjectNode objectNode = (ObjectNode) array.get(j);
            Movie movie = new Movie(objectNode.get("title").textValue(),
                objectNode.get("year").intValue(), objectNode.get("response").intValue(),
                objectNode.get("genre").textValue());
            movies.add(movie);
          }
        }
      }
    }
    return movies;
  }

  /**
   * Метод, який перевіряє існування основної директорії
   * @throws IOException вийняток
   */
  public void checkFile() throws IOException {
    if (!directory.exists()) {
      System.out.println(ANSI_RED +"Немає папки library!!! Створюємо нову папку...");
      File directoryMain = new File(PATH_LIBRARY);
      directoryMain.mkdir();
    }
  }

}
