package com.alura.literatura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "libros" )

public class Libro {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;
    private String titulo;
    private String idioma;
    private Integer numeroDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {
    }

    public Libro(String titulo, String idioma, Integer numeroDescargas, Autor autor) {

        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public Long getId() {

        return id;
    }

    public String getTitulo() {

        return titulo;
    }

    public String getIdioma() {

        return idioma;
    }

    public Integer numeroDescargas() {

        return numeroDescargas;
    }

   public Autor getAutor() {

        return autor;
    }

    @Override
    public String toString() {
        return "📖 Libro: " + titulo +
                "\n✍ Autor: " + (autor != null? autor.getNombre() : "Autor desconocido")+
                "\n🌍 Idioma: " + idioma +
                "\n⬇ Descargas: " + numeroDescargas+
                "\n---------------------------------";
    }
}
