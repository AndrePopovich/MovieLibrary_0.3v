package com.andre.ui;

import static com.andre.ui.ConsoleColor.ANSI_BLUE;
import static com.andre.ui.ConsoleColor.ANSI_CYAN;
import static com.andre.ui.ConsoleColor.ANSI_GREEN;
import static com.andre.ui.ConsoleColor.ANSI_PURPLE;
import static com.andre.ui.ConsoleColor.ANSI_RED;
import static com.andre.ui.ConsoleColor.ANSI_RESET;
import static com.andre.ui.ConsoleColor.ANSI_YELLOW;

import com.andre.crud.Crud;
import com.andre.model.User;
import com.andre.sf.Filter;
import com.andre.sf.Seach;
import com.andre.sf.Sort;
import java.io.Console;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Клас інтерфейсу користувача
 */
public class UI {
  /**
   * Це зміна, в якій буде зберігатись відповідь користувача
   */
  private String num;
  /**
   * Це зміна, в якій буде зберігатись логін користувача
   */
  private String login;
  /**
   * Це зміна, в якій буде зберігатись пароль користувача
   */
  private char[] readPassword;
  /**
   * Це зміна, яка буде показувати пройдена авторизація або реєстрація користувача
   */
  private boolean check;

  /**
   * Це зміна класу консоль
   */
  private Console cnsl = System.console();
  /**
   * Змінна, яка зчитує дані введені користувачем
   */
  private Scanner s1 = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
  /**
   * Змінна, яка зберігає в собі об'єкт CRUD
   */
  private Crud crud = new Crud();

  /**
   *Початкова точка запуску інтерфейсу
   * @throws IOException вийняток
   */

  public void UserInterface() throws IOException {
    System.out.print(ANSI_PURPLE);
    System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
    System.out.println("_-_-_-_ Вітаємо Вас в КІНОТЕЦІ!!! _-_-_-_");
    System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    startMenu();
  }

  /**
   * Метод початкового меню
   * @throws IOException вийняток
   */
  public void startMenu() throws IOException {
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.println(ANSI_BLUE+"\n\n_-_-_-_ ПОЧАТКОВЕ МЕНЮ _-_-_-_" +ANSI_YELLOW+
        "\n1. Авторизація" +
        "\n2. Регістрація" +
        "\n3. Переглянути список фільмів" +
        "\n4. Вихід з програми");
    System.out.print(ANSI_CYAN+"Оберіть варіант входу: " + ANSI_RESET);
    num = s.nextLine();
    switch (num) {
      case "1":
        if (cnsl == null) {
          System.out.println(ANSI_RED + "Немає доступу до консолі");
          break;
        }
        System.out.print(ANSI_BLUE+"Введіть логін: ");
        login = s.nextLine();
        if (login.length() >= 4 && login.length() <= 20){
          readPassword = cnsl.readPassword(ANSI_YELLOW+"Введіть пароль: ");
          if(readPassword.length <= 20 && readPassword.length >= 4){
            User user = new User(login, new String(readPassword));
            check = user.Authorization();
            if (check) {
              System.out.println(ANSI_GREEN+"Ви успішно ввійшли в акаунт!!!");
              mainMenu();
            } else {
              System.out.println(ANSI_RED+"Невірний логін або пароль!!!");
              startMenu();
            }
          } else {
            System.out.println(ANSI_RED+"Довжина паролю повина бути від 4 до 20 символів!!!");
            startMenu();
          }
        } else {
          System.out.println(ANSI_RED+"Довжина логіну повина бути від 4 до 20 символів!!!");
          startMenu();
        }

        break;

      case "2":
        if (cnsl == null) {
          System.out.println(ANSI_RED + "Немає доступу до консолі");
          break;
        }
        System.out.print(ANSI_BLUE+"Введіть новий логін: ");
        login = s.nextLine();
        if (login.length() >= 4 && login.length() <= 20){
          readPassword = cnsl.readPassword(ANSI_YELLOW+"Введіть новий пароль: ");
          if(readPassword.length <= 20 && readPassword.length >= 4){
            User newUser = new User(login, new String(readPassword));
            check = newUser.Registration();
            if (check) {
              System.out.println(ANSI_GREEN+"Ви успішно зареєструвалися!!!" + ANSI_RESET);
              startMenu();
            } else {
              System.out.println(ANSI_RED+"Такий акаунт уже існує!!!" + ANSI_RESET);
              startMenu();
            }
          } else {
            System.out.println(ANSI_RED+"Довжина паролю повина бути від 4 до 20 символів!!!");
            startMenu();
          }

        } else {
          System.out.println(ANSI_RED+"Довжина логіну повина бути від 4 до 20 символів!!!");
          startMenu();
        }
        break;

      case "3":
        crud.Read();
        startMenu();
        break;

      case "4":
        System.out.println(ANSI_GREEN+ "\n_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_ Ви вийшли з програми _-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + ANSI_RESET);
        System.exit(0);

      default:
        System.out.println(ANSI_RED+"Неправильний вибір!!! Спробуйте ще раз..." + ANSI_RESET);
        startMenu();
        break;
    }
  }

  /**
   * Метод головного меню
   * @throws IOException вийняток
   */
  public void mainMenu() throws IOException{
    Scanner s = new Scanner(System.in, Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251":"UTF-8"));
    System.out.println(ANSI_BLUE+"\n\n_-_-_-_ ГОЛОВНЕ МЕНЮ _-_-_-_" +ANSI_YELLOW+
        "\n1. Додати новий фільм" +
        "\n2. Переглянути список фільмів" +
        "\n3. Редагувати фільмів" +
        "\n4. Видалити фільм" +
        "\n5. Сортувати всі фільми по рейтингу" +
        "\n6. Фільтрувати всі фільми по жанру" +
        "\n7. Видалити всі фільми" +
        "\n8. Пошук фільму по назві" +
        "\n9. Вихід з акаунту" +
        "\n10. Вихід з програми");
    System.out.print(ANSI_CYAN+"Оберіть варіант входу: " + ANSI_RESET);
    num = s.nextLine();
    switch (num){
      case "1":
        crud.Create();
        mainMenu();
        break;
      case "2":
        crud.Read();
        mainMenu();
        break;
      case "3":
        crud.Update();
        mainMenu();
        break;
      case "4":
        crud.Delete();
        mainMenu();
        break;
      case "5":
        Sort sort = new Sort();
        sort.sort();
        break;
      case "6":
        Filter filter = new Filter();
        filter.filter();
        break;
      case "7":
        System.out.print(ANSI_CYAN+"\nВи справді хочете видалити всі фільми?\n"+ ANSI_YELLOW +
            "1. Так\n" +
            "2. Ні\n" + ANSI_PURPLE +
            "Оберіть варіант: ");
        num = s1.nextLine();
        switch (num){
          case "1":
            crud.removeAllMovies();
            break;
          case "2":
            System.out.println(ANSI_RED + "Ви відмовились видаляти фільми!!!");
            break;
          default:
            System.out.println(ANSI_RED +"Невірні дані!!!");
            break;
        }
        mainMenu();
        break;
      case "8":
        Seach seach = new Seach();
        seach.search();
        break;
      case "9":
        startMenu();
        break;
      case "10":
        System.out.println(ANSI_GREEN+ "\n_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_ Ви вийшли з програми _-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + ANSI_RESET);
        System.exit(0);
      default:
        System.out.println(ANSI_RED +"Невірні дані!!!");
        mainMenu();
        break;
    }

  }
}
