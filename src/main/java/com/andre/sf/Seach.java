package com.andre.sf;

import static com.andre.ui.ConsoleColor.ANSI_CYAN;
import static com.andre.ui.ConsoleColor.ANSI_GREEN;
import static com.andre.ui.ConsoleColor.ANSI_PURPLE;
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
 * Клас пошуку фільмів
 */
public class Seach extends Unifier{

  /**
   * Метод пошуку фільмів
   * @throws IOException вийняток
   */
  public void search() throws IOException {
    UI ui = new UI();
    List<Movie> movies = new ArrayList<>();
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.print(ANSI_YELLOW +"Введіть назву фільму, який хочете знайти: ");
    String title = s.nextLine();
    unitedFiles(movies);
    movies = movies.stream()
        .filter(element-> element.getTitle().equals(title))
        .collect(Collectors.toList());
    if(movies.size() == 0){
      System.out.println(ANSI_RED+"Фільма з такою назвою немає!!!");
    }
    else {
      System.out.println(ANSI_PURPLE+"\n_-_-_ Знайдено фільм: " +ANSI_GREEN +title+ANSI_PURPLE + " _-_-_");
      System.out.println(ANSI_CYAN+movies.toString().replaceAll("\\[","").replaceAll("]",""));
    }
    ui.mainMenu();
  }

}
