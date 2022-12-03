package com.andre.model;

import java.util.List;

/**
 * Клас кінотеки
 */
public class Library {

  /**
   * Масив фільмів
   */
  private List<Movie> movies;

  /**
   * Метод який повертає фільм
   * @return повертає фільм
   */

  public List<Movie> getMovies() {
    return movies;
  }

  /**
   * Конструктор
   */
  public Library(){

  }

  /**
   * Метод, який встановлює змінну
   * @param movies приймає фільми
   */
  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }

  /**
   * Перевийзначення toString
   * @return повертає фільми
   */
  @Override
  public String toString(){
    return "\n_-_-_ Фільми _-_-_"+movies;
  }
}
