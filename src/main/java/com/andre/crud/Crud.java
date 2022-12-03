package com.andre.crud;

import static com.andre.ui.ConsoleColor.ANSI_BLUE;
import static com.andre.ui.ConsoleColor.ANSI_CYAN;
import static com.andre.ui.ConsoleColor.ANSI_GREEN;
import static com.andre.ui.ConsoleColor.ANSI_RED;
import static com.andre.ui.ConsoleColor.ANSI_YELLOW;

import com.andre.model.Library;
import com.andre.model.Movie;
import com.andre.ui.UI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Crud {

  /**
   * Змінна, яка зберігає шлях до головної папки кінотеки
   */
  private final String PATH_LIBRARY = "library";

  private ObjectMapper mapper = new ObjectMapper();
  /**
   * Змінна, яка зберігає назву фільма
   */
  private String title;
  /**
   * Змінна, яка зберігає рік фільма
   */
  private int year;
  /**
   * Змінна, яка зберігає відгук фільма
   */
  private int response;
  /**
   * Змінна, яка зберігає жанр фільма
   */
  private String genre;

  /**
   * Метод створення файлів або фільмів
   * @throws IOException вийняток
   */
  public void Create()
      throws IOException {
    writeData("Create");
    checkFile(year);

    String path = "library\\"+ Integer.toString(year) + "\\movies.json";
    ObjectNode root = mapper.createObjectNode();
    JsonNode jsonNode = mapper.readTree(new File(path));

    ArrayNode movies = (ArrayNode) jsonNode.get("movies");
    ObjectNode item = mapper.createObjectNode();
    item.put("title", title);
    item.put("year", year);
    item.put("response", response);
    item.put("genre", genre);
    movies.add(item);
    root.put("movies",movies);

    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)));
    writer.println(mapper.writeValueAsString(root));
    writer.close();
    System.out.println("Записано в файл: " + root);
  }

  /**
   * Метод для читання файлів або фільмів
   * @throws IOException вийняток
   */
  public void Read() throws IOException {
    String pathToMovies = findFile();

    if(pathToMovies.equals("В вашій кінотеці немає фільмів!!!")) {
      System.out.println(ANSI_RED+pathToMovies);
    }
    else {
      File file = new File(pathToMovies);
        if(!file.exists()){
          System.out.println(ANSI_RED +"В Вашій кінотеці такого року неіснує!!!");
        }
        else {
          File fileLibrary = new File(pathToMovies);
          Library library = mapper.readValue(fileLibrary, Library.class);
          System.out.println(library.toString().replaceAll("\\[","").replaceAll("]",""));
        }
      }

  }
  /**
   * Метод для редагування файлів або фільмів
   * @throws IOException вийняток
   */
  public void Update() throws IOException {
    String pathToMovies = findFile();
    if(pathToMovies.equals(ANSI_RED +"В вашій кінотеці немає фільмів!!!")) {
      System.out.println(pathToMovies);
    }
    else {
      File file = new File(pathToMovies);
      if(!file.exists()){
        System.out.println(ANSI_RED +"В Вашій кінотеці такого року неіснує!!!");
      }
      else {
        File fileLibrary = new File(pathToMovies);
        Library library = mapper.readValue(fileLibrary, Library.class);
        System.out.println(library.toString().replaceAll("\\[","").replaceAll("]",""));
        System.out.print(ANSI_CYAN +"Введіть назву фільма, який хочете редагувати: ");
        Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
        String nameMovie = s.nextLine();

        ObjectNode root = (ObjectNode) mapper.readTree(fileLibrary);
        ArrayNode array = (ArrayNode) root.get("movies");
        writeData("Update");

        for (int i = 0; i < array.size(); i++) {
          if (array.get(i).get("title").asText().equals(nameMovie)) {
            ObjectNode objectNode = (ObjectNode) array.get(i);
            objectNode.put("title", title);
            objectNode.put("response", response);
            objectNode.put("genre",genre);
            //array.remove(i);
            System.out.println(ANSI_GREEN +"Дані відредаговано!!!");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileLibrary)));
            writer.println(mapper.writeValueAsString(root));
            writer.close();

            break;
          }
          else if((i+1) == array.size()){
            System.out.println(ANSI_RED +"Такого фільма немає!!!");
          }
        }
      }
    }
  }
  /**
   * Метод для видалення файлів або фільмів
   * @throws IOException вийняток
   */
  public void Delete() throws IOException {
    String pathToMovies = findFile();
    if(pathToMovies.equals(ANSI_RED +"В вашій кінотеці немає фільмів!!!")) {
      System.out.println(pathToMovies);
    }
    else {
      File file = new File(pathToMovies);
      if(!file.exists()){
        System.out.println(ANSI_RED +"В Вашій кінотеці такого року неіснує!!!");
      }
      else {
        File fileLibrary = new File(pathToMovies);
        Library library = mapper.readValue(fileLibrary, Library.class);
        System.out.println(library.toString().replaceAll("\\[","").replaceAll("]",""));
        System.out.print(ANSI_CYAN +"Введіть назву фільма, який хочете видалити: ");
        Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
        String nameMovie = s.nextLine();
        ObjectNode root = (ObjectNode) mapper.readTree(fileLibrary);
        ArrayNode array = (ArrayNode) root.get("movies");

        for (int i = 0; i < array.size(); i++) {
          if (array.get(i).get("title").asText().equals(nameMovie)) {
            String yearMovie = array.get(i).get("year").asText();
            System.out.println(ANSI_GREEN +"Фільм успішно видалено!!!");

            array.remove(i);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileLibrary)));
            writer.println(mapper.writeValueAsString(root));
            writer.close();
            array = (ArrayNode) root.get("movies");
            if(array.size() == 0){
              recursiveDelete(new File(PATH_LIBRARY + "\\" + yearMovie));
            }

            break;
          }
          else if((i+1) == array.size()){
            System.out.println(ANSI_RED +"Такого фільма немає!!!");
          }
        }
      }
    }
  }

  /**
   * Метод для видалення всіх фільмів
   */
  public void removeAllMovies(){
    File file = new File("library");
    if(!file.exists()){
      System.out.println(ANSI_RED+"Помилка!!! Немає, що видаляти...");
    }
    else {
      recursiveDelete(file);
      System.out.println(ANSI_GREEN + "Всі фільми успішно видалено!!!");
    }
  }

  /**
   * Метод для видалення каталогу вказаного файла
   * @param file приймає файл або папку, який потрібно видалити
   */
  private void recursiveDelete(File file) {
    if (!file.exists())
      return;

    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        recursiveDelete(f);
      }
    }
    file.delete();
    System.out.println(ANSI_GREEN +"Видалено: " + file.getAbsolutePath());
  }

  /**
   * Шукає шлях до файлу
   * @return повертає шлях до файлу
   */
  public String findFile() {
    File folder = new File(PATH_LIBRARY);
    if (!folder.exists()) {
      folder.mkdir();
    }
    File[] listOfFiles = folder.listFiles();
    if (listOfFiles.length <= 0) {
      return "В вашій кінотеці немає фільмів!!!";
    } else {
      System.out.println(ANSI_BLUE +"_-_-_ Список папок _-_-_");
      for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isDirectory()) {
          System.out.println(ANSI_YELLOW +(i + 1) + ". Фільми " + listOfFiles[i].getName() + " року");
        }
      }
      System.out.print(ANSI_CYAN +"Введіть рік фільмів, які б ви хотіли побачити: ");
      Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
      String year = s.nextLine();
      return "library" + "\\" + year + "\\movies.json";
    }
  }

  /**
   * Створення нового об'єкту
   * @param type приймає тип створення (може бути Create or Update)
   * @throws IOException вийняток
   */
  public void writeData(String type) throws IOException {
    UI ui = new UI();
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    Scanner s1 = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.print(ANSI_BLUE +"Введіть назву фільму: ");
    title = s.nextLine();
    if(type.equals("Create")){
      System.out.print(ANSI_YELLOW +"Введіть рік фільму: ");
      year = s.nextInt();
      if(year < 1900 || year > 2023){
        System.out.println(ANSI_RED+"Помилка!!! Фільм повинен бути від 1900 до 2023 року!!!");
        ui.mainMenu();
      }
    }

    System.out.print(ANSI_CYAN +"\n1. 1 бал"+ "\n2. 2 бала"+ "\n3. 3 бала"+ "\n4. 4 бала"+ "\n5. 5 балів"+
        ANSI_YELLOW +"\nОберіть відгук фільму: ");
    String num = s1.nextLine();
    switch (num){
      case "1":
        response = 1;
        break;
      case "2":
        response = 2;
        break;
      case "3":
        response = 3;
        break;
      case "4":
        response = 4;
        break;
      case "5":
        response = 5;
        break;
      default:
        System.out.println(ANSI_RED +"Невірні дані!!!");
        ui.mainMenu();
        break;
    }
    System.out.print(ANSI_CYAN +"\n1. Жахи"+ "\n2. Комедія"+ "\n3. Бойовик"+ "\n4. Тріллер"+ "\n5. Мультфільми"+
        ANSI_YELLOW +"\nОберіть жанр фільму: ");
    genre = s1.nextLine();
    switch (genre){
      case "1":
        genre = "Жахи";
        break;
      case "2":
        genre = "Комедія";
        break;
      case "3":
        genre = "Бойовик";
        break;
      case "4":
        genre = "Тріллер";
        break;
      case "5":
        genre = "Мультфільм";
        break;
      default:
        System.out.println(ANSI_RED +"Невірні дані!!!");
        ui.mainMenu();
        break;
    }
  }

  /**
   * Метод який перевіряє, чи існує певна директорія
   * @param year приймає рік
   * @throws IOException вийняток
   */
  public void checkFile(int year) throws IOException {
    File directory = new File(PATH_LIBRARY);
    if(!directory.exists()) {
      System.out.println(ANSI_RED +"Немає головної папки library!!!"+ANSI_GREEN+"\nСтворюємо нову папку...");
      File directoryMain = new File(PATH_LIBRARY);
      directoryMain.mkdir();
    }
    String path = "library\\"+ Integer.toString(year);
    File moviesYear = new File(path);
    if(!moviesYear.exists()){
      moviesYear.mkdir();
    }
    File file = new File(path + "\\movies.json");
    if(!file.exists()){
      file.createNewFile();
      ObjectNode root = mapper.createObjectNode();
      ArrayNode arrayNode = mapper.createArrayNode();
      root.put("movies",arrayNode);

      PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
      writer.println(mapper.writeValueAsString(root));
      writer.close();
    }
  }

}
