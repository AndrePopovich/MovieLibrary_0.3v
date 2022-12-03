package com.andre.model;

/**
 * Клас фільмів
 */
public class Movie {

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
     * Метод конструктор
     */
    public Movie(String title, int year,int response, String genre){
      this.title = title;
      this.year = year;
      this.response = response;
      this.genre = genre;
    }

  /**
   * Конструктор
   */
  public Movie(){

    }

    /**
     * Метод, який повертає назву фільма
     * @return повертає назву фільма
     */
    public String getTitle() {
      return title;
    }
    /**
     * Метод, який повертає рік фільма
     * @return повертає рік фільма
     */
    public int getYear() {
      return year;
    }
    /**
     * Метод, який повертає відгук фільма
     * @return повертає відгук фільма
     */
    public int getResponse() {
      return response;
    }
    /**
     * Метод, який повертає жанр фільма
     * @return повертає жанр фільма
     */
    public String getGenre() {
      return genre;
    }
    /**
     * Метод, який повертає інформацію
     * @return повертає інформацію
     */
    @Override
    public String toString(){
      return  "\n\nНазва фільму: " + title +
          "\nРік випуску: " + year +
          "\nВідгук: " + response +
          "\nЖанр: " + genre;
    }


}
