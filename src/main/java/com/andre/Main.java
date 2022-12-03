package com.andre;

import static com.andre.ui.ConsoleColor.ANSI_GREEN;

import com.andre.crud.Crud;
import com.andre.ui.UI;
import java.io.File;
import java.io.IOException;
/**
 * @author Andriy Popovich
 * Це клас Main він містить в собі метод main
 */
public class Main {
  /**
   * Це початкова точка входу програми
   * @param args
   * @throws IOException виключення
   */
  public static void main(String[] args) throws IOException {
    UI ui = new UI();
    ui.UserInterface();

  }

}
