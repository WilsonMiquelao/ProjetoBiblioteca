package projeto_Biblioteca;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private Cliente cliente;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(int id, Cliente cliente, Livro livro, LocalDate dataEmprestimo) {
        this.id = id;
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    // Realiza a devolução do livro
    public void devolverLivro(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        livro.setDisponivel(true);
    }

    @Override
    public String toString() {
        return "Emprestimo " + id + " - Cliente: " + cliente.getNome() + ", Livro: " + livro.getTitulo() +
                ", Emprestado em: " + dataEmprestimo +
                (dataDevolucao != null ? ", Devolvido em: " + dataDevolucao : ", Ainda emprestado");
    }
}
