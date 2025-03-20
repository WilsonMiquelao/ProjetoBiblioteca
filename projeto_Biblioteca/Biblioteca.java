package projeto_Biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Biblioteca {
    private List<Livro> livros;
    private List<Cliente> clientes;
    private List<Emprestimo> emprestimos;
    private int nextEmprestimoId = 1;
    private int nextClienteId = 1;
    private int nextLivroId = 1;

    public Biblioteca() {
        livros = new ArrayList<>();
        clientes = new ArrayList<>();
        emprestimos = new ArrayList<>();
    }

    // Cadastro de livro (novo livro fica disponível para empréstimo)
    public void adicionarLivro(String titulo, String autor, String genero) {
        Livro livro = new Livro(nextLivroId++, titulo, autor, genero, LocalDate.now());
        livros.add(livro);
        System.out.println("Livro adicionado: " + livro);
    }

    // Busca por título (contém)
    public List<Livro> buscarLivroPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Busca por autor (contém)
    public List<Livro> buscarLivroPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Filtra livros por gênero
    public List<Livro> filtrarPorGenero(String genero) {
        return livros.stream()
                .filter(livro -> livro.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    // Filtra livros adicionados recentemente (por exemplo, nos últimos 30 dias)
    public List<Livro> filtrarPorRecentes() {
        LocalDate limite = LocalDate.now().minusDays(30);
        return livros.stream()
                .filter(livro -> livro.getDataAdicao().isAfter(limite))
                .collect(Collectors.toList());
    }

    // Retorna a lista de livros cadastrados
    public List<Livro> getLivros() {
        return livros;
    }

    // Cadastro de cliente
    public void cadastrarCliente(String nome, LocalDate dataNascimento, String email) {
        Cliente cliente = new Cliente(nextClienteId++, nome, dataNascimento, email);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado: " + cliente);
    }

    // Lista todos os clientes
    public List<Cliente> listarClientes() {
        return clientes;
    }

    // Realiza empréstimo de um livro a um cliente (associando o empréstimo ao cliente)
    public void emprestarLivro(int clienteId, int livroId) {
        Cliente cliente = clientes.stream().filter(c -> c.getId() == clienteId).findFirst().orElse(null);
        Livro livro = livros.stream().filter(l -> l.getId() == livroId).findFirst().orElse(null);
        if(cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        if(livro == null) {
            System.out.println("Livro não encontrado!");
            return;
        }
        if(!livro.isDisponivel()) {
            System.out.println("Livro indisponível para empréstimo.");
            return;
        }
        Emprestimo emprestimo = new Emprestimo(nextEmprestimoId++, cliente, livro, LocalDate.now());
        emprestimos.add(emprestimo);
        cliente.addEmprestimo(emprestimo);
        livro.setDisponivel(false);
        System.out.println("Empréstimo realizado: " + emprestimo);
    }

    // Registra a devolução de um livro a partir do ID do empréstimo
    public void devolverLivro(int emprestimoId) {
        Emprestimo emprestimo = emprestimos.stream().filter(e -> e.getId() == emprestimoId).findFirst().orElse(null);
        if(emprestimo == null) {
            System.out.println("Empréstimo não encontrado!");
            return;
        }
        if(emprestimo.getDataDevolucao() != null) {
            System.out.println("Livro já foi devolvido!");
            return;
        }
        emprestimo.devolverLivro(LocalDate.now());
        System.out.println("Livro devolvido: " + emprestimo);
    }

    // Consulta o histórico de empréstimos de um cliente
    public List<Emprestimo> historicoCliente(int clienteId) {
        return emprestimos.stream()
                .filter(e -> e.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    // Consulta o histórico de empréstimos de um livro
    public List<Emprestimo> historicoLivro(int livroId) {
        return emprestimos.stream()
                .filter(e -> e.getLivro().getId() == livroId)
                .collect(Collectors.toList());
    }

    // Retorna a lista completa de empréstimos (incluindo os já devolvidos)
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
}
