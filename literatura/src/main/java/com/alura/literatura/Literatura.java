package com.alura.literatura;

import com.alura.literatura.service.ConsumoAPI;

public class Literatura {

 public static void main(String[] args) {
    ConsumoAPI consumoAPI = new ConsumoAPI();

    String url = "https://gutendex.com/books/?search=harry";
    String json = consumoAPI.obtenerDatos(url);

    System.out.println(json);
}
}