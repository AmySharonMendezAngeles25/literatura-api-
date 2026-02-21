package com.alura.literatura.principal;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.DatosLibros;
import com.alura.literatura.model.DatosRespuesta;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("--------------------------------------");
            System.out.println("Elige una opción:");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Mostrar libros guardados");
            System.out.println("3 - Mostrar autores guardados");
            System.out.println("4 - Mostrar autores vivos en un año");
            System.out.println("5 - Mostrar cantidad de libros por idioma");
            System.out.println("0 - Salir");
            System.out.println("--------------------------------------");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;

                case 2:
                    mostrarLibros();
                    break;

                case 3:
                    mostrarAutores();
                    break;

                case 4:
                    mostrarAutoresVivosPorAnio();
                    break;

                case 5:
                    mostrarCantidadLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibroPorTitulo() {

        System.out.println("Escribe el nombre del libro:");
        String titulo = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        String json = consumoAPI.obtenerDatos(url);

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("No se encontró el libro.");
            return;
        }

        DatosLibros datosLibro = respuesta.resultados().get(0);

        // 🔹 Obtener datos del autor (solo el primero)
        var datosAutor = datosLibro.autores().get(0);

        // 🔹 Verificar si el autor ya existe
        Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(
                            datosAutor.nombre(),
                            datosAutor.fechaNacimiento(),
                            datosAutor.fechaFallecimiento()
                    );
                    return autorRepository.save(nuevoAutor);
                });

        // 🔹 Idioma
        String idioma = datosLibro.idiomas().isEmpty()
                ? "N/A"
                : datosLibro.idiomas().get(0);

        // 🔹 Crear libro con relación al autor
        Libro libro = new Libro(
                datosLibro.titulo(),
                idioma,
                datosLibro.numeroDescargas(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("\n📖 Libro guardado correctamente:");
        System.out.println(libro);
    }

    private void mostrarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
            return;
        }

        libros.forEach(System.out::println);
    }

    private void mostrarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void mostrarAutoresVivosPorAnio() {

        System.out.println("Ingrese el año para buscar autores vivos : ");

        try {
            Integer anio = Integer.parseInt(teclado.nextLine());

            if (anio < 0) {
                System.out.println("Ingresa un año válido.");
                return;
            }

            List<Autor> autores = autorRepository.autoresVivosEnAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }

        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número válido.");
        }
    }

    private void mostrarCantidadLibrosPorIdioma() {
        long cantidadES = libroRepository.countByIdioma("es");
        long cantidadEN = libroRepository.countByIdioma("en");

        System.out.println("📊 Estadísticas por idioma:");
        System.out.println("🌎 Español (es): " + cantidadES + " libros");
        System.out.println("🌎 Inglés (en): " + cantidadEN + " libros");
    }
}