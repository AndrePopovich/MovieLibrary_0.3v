package com.andre.sf;

import static com.andre.ui.ConsoleColor.ANSI_CYAN;
import static com.andre.ui.ConsoleColor.ANSI_RED;
import static com.andre.ui.ConsoleColor.ANSI_YELLOW;

import com.andre.model.Library;
import com.andre.model.Movie;
import com.andre.ui.UI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Клас для сортування даних
 */
public class Sort extends Unifier{

  /**
   * Метод, який сортує масив
   * @throws IOException
   */
  public void sort() throws IOException{
    UI ui = new UI();
    List<Movie> movies = new ArrayList<>();
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.println(ANSI_CYAN +"1. Сортувати по зростаню\n2. Сортувати по спаданю");
    System.out.print(ANSI_YELLOW +"Оберіть вид сортування: ");
    String num = s.nextLine();
    unitedFiles(movies);
    switch (num){
      case "1":
        movies = movies.stream()
            .sorted(Comparator.comparingInt(Movie::getResponse))
            .collect(Collectors.toList());
        System.out.println(movies.toString().replaceAll("\\[","").replaceAll("]",""));
        break;
      case "2":
        movies = movies.stream()
            .sorted(Comparator.comparingInt(Movie::getResponse).reversed())
            .collect(Collectors.toList());
        System.out.println(movies.toString().replaceAll("\\[","").replaceAll("]",""));
        break;
      default:
        System.out.println(ANSI_RED +"Невірні дані!!!");
        break;
    }
    ui.mainMenu();
  }
}
