package projeto_Biblioteca;

import java.time.LocalDate;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private LocalDate dataAdicao;
    private boolean disponivel;

    //construtor livro
    public Livro(int id, String titulo, String autor, String genero, LocalDate dataAdicao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.dataAdicao = dataAdicao;
        this.disponivel = true;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getDataAdicao() {
        return dataAdicao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro " + id + " - " + titulo + " por " + autor + " [" + genero + "]";
    }
}
