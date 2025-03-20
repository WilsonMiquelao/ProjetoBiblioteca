package projeto_Biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String email;
    private List<Emprestimo> emprestimos;

    public Cliente(int id, String nome, LocalDate dataNascimento, String email) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.emprestimos = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    // Método para associar um empréstimo ao cliente
    public void addEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    @Override
    public String toString() {
        return "Cliente " + id + " - " + nome + " (" + email + ")";
    }
}
