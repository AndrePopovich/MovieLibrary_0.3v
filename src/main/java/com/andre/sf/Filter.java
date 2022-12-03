package com.andre.sf;

import static com.andre.ui.ConsoleColor.ANSI_CYAN;
import static com.andre.ui.ConsoleColor.ANSI_RED;
import static com.andre.ui.ConsoleColor.ANSI_YELLOW;

import com.andre.model.Movie;
import com.andre.ui.UI;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Клас для фільтрації даних
 */
public class Filter extends Unifier {

  /**
   * Змінна, яка зберігає жанр
   */
  private String genre;

  /**
   * Метод, який фільтрує масив
   * @throws IOException вийняток
   */
  public void filter() throws IOException {
    UI ui = new UI();
    List<Movie> movies = new ArrayList<>();
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.print(ANSI_CYAN +"\n1. Жахи"+ "\n2. Комедія"+ "\n3. Бойовик"+ "\n4. Тріллер"+ "\n5. Мультфільми"+
        ANSI_YELLOW +"\nОберіть жанр фільму: ");
    String num = s.nextLine();
    unitedFiles(movies);
    switch (num){
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
    movies = movies.stream()
        .filter(element-> element.getGenre().equals(genre))
        .collect(Collectors.toList());
    if(movies.size() == 0){
      System.out.println(ANSI_RED +"Немає фільмів такого жанру!!!");
    }
    else {
      System.out.println(movies.toString().replaceAll("\\[","").replaceAll("]",""));
    }
    ui.mainMenu();
  }
}
