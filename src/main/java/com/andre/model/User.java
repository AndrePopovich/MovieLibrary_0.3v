package com.andre.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
  /**
   * Зміна, яка зберігає логін
   */
  private String login;
  /**
   * Зміна, яка зберігає пароль
   */
  private String password;
  /**
   * Зміна, яка шлях до файлу з акаунтами
   */
  private final String PATH = "accounts.txt";
  /**
   * Зміна, яка зберігає файл
   */
  private File file = new File(PATH);

  /**
   * Метод конструктор користувача
   * @param login приймає логін
   * @param password приймає пароль
   */
  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  /**
   * Метод авторизації
   * @return повертає true або false
   * @throws IOException
   */
  public boolean Authorization() throws IOException {
    if(file.exists()){
      String[] array = null;
      BufferedReader reader = new BufferedReader(new FileReader(PATH));
      String str;
      password = HashPassword(password);
      while ((str = reader.readLine()) != null) {
        array = str.split(", ");
        if (array[0].equals(login) && array[1].equals(password)) {
          reader.close();
          return true;
        }
      }
      reader.close();
      return false;
    }
    else {
      return false;
    }


  }

  /**
   * Метод реєстрації
   * @return повертає true або false
   * @throws IOException
   */
  public boolean Registration() throws IOException {
    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH, true)));
    if(file.exists()){
      String[] array = null;
      BufferedReader reader = new BufferedReader(new FileReader(PATH));
      String str;
      while ((str = reader.readLine()) != null) {
        array = str.split(",");
        if (array[0].equals(login)) {
          reader.close();
          return false;
        }
      }
      reader.close();
      password = HashPassword(password);
      writer.println(login + ", " + password);
      writer.close();

      return true;
    } else{
      password = HashPassword(password);
      writer.println(login + ", " + password);
      writer.close();
      return true;
    }

  }

  /**
   * Метод, який хешує пароль
   * @param password приймає пароль
   * @return повертає хешований пароль
   */
  private String HashPassword(String password) {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
      md5.update(password.getBytes(), 0, password.length());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return new BigInteger(1, md5.digest()).toString(16);
  }

}
