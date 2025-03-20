package projeto_Biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scanner = new Scanner(System.in);
    // Formato para entrada de datas: dd/MM/yyyy
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean running = true;
        while(running) {
            System.out.println("\n===== MENU DA BIBLIOTECA =====");
            System.out.println("1. Cadastrar novo livro");
            System.out.println("2. Cadastrar novo cliente");
            System.out.println("3. Listar livros");
            System.out.println("4. Buscar livro por título");
            System.out.println("5. Buscar livro por autor");
            System.out.println("6. Filtrar livros por gênero");
            System.out.println("7. Listar livros adicionados recentemente");
            System.out.println("8. Realizar empréstimo");
            System.out.println("9. Devolver livro");
            System.out.println("10. Histórico de empréstimos de um cliente");
            System.out.println("11. Histórico de empréstimos de um livro");
            System.out.println("12. Listar clientes");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch(opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    listarLivros();
                    break;
                case 4:
                    buscarLivroPorTitulo();
                    break;
                case 5:
                    buscarLivroPorAutor();
                    break;
                case 6:
                    filtrarPorGenero();
                    break;
                case 7:
                    listarRecentes();
                    break;
                case 8:
                    realizarEmprestimo();
                    break;
                case 9:
                    devolverLivro();
                    break;
                case 10:
                    historicoCliente();
                    break;
                case 11:
                    historicoLivro();
                    break;
                case 12:
                    listarClientes();
                    break;
                case 0:
                    running = false;
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarLivro() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o autor do livro: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o gênero do livro: ");
        String genero = scanner.nextLine();
        biblioteca.adicionarLivro(titulo, autor, genero);
    }

    private static void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataStr, dtf);
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        biblioteca.cadastrarCliente(nome, dataNascimento, email);
    }

    private static void listarLivros() {
        System.out.println("Livros cadastrados:");
        biblioteca.getLivros().forEach(System.out::println);
    }

    private static void buscarLivroPorTitulo() {
        System.out.print("Digite o título para busca: ");
        String titulo = scanner.nextLine();
        var resultados = biblioteca.buscarLivroPorTitulo(titulo);
        if(resultados.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private static void buscarLivroPorAutor() {
        System.out.print("Digite o autor para busca: ");
        String autor = scanner.nextLine();
        var resultados = biblioteca.buscarLivroPorAutor(autor);
        if(resultados.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private static void filtrarPorGenero() {
        System.out.print("Digite o gênero para filtrar: ");
        String genero = scanner.nextLine();
        var resultados = biblioteca.filtrarPorGenero(genero);
        if(resultados.isEmpty()) {
            System.out.println("Nenhum livro encontrado para esse gênero.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private static void listarRecentes() {
        var resultados = biblioteca.filtrarPorRecentes();
        if(resultados.isEmpty()) {
            System.out.println("Nenhum livro recente encontrado.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private static void realizarEmprestimo() {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o ID do livro: ");
        int livroId = Integer.parseInt(scanner.nextLine());
        biblioteca.emprestarLivro(clienteId, livroId);
    }

    private static void devolverLivro() {
        System.out.print("Digite o ID do empréstimo: ");
        int emprestimoId = Integer.parseInt(scanner.nextLine());
        biblioteca.devolverLivro(emprestimoId);
    }

    private static void historicoCliente() {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        var historico = biblioteca.historicoCliente(clienteId);
        if(historico.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado para esse cliente.");
        } else {
            historico.forEach(System.out::println);
        }
    }

    private static void historicoLivro() {
        System.out.print("Digite o ID do livro: ");
        int livroId = Integer.parseInt(scanner.nextLine());
        var historico = biblioteca.historicoLivro(livroId);
        if(historico.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado para esse livro.");
        } else {
            historico.forEach(System.out::println);
        }
    }

    private static void listarClientes() {
        System.out.println("Clientes cadastrados:");
        biblioteca.listarClientes().forEach(System.out::println);
    }
}
