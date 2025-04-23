package projeto_Biblioteca;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BibliotecaAppFX extends Application {

    private Biblioteca biblioteca = new Biblioteca();
    // Formato para entrada de datas: dd/MM/yyyy
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Biblioteca");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // Botões com os textos atualizados
        Button btnListarLivros = new Button("Livros cadastrados");
        Button btnListarLivrosDisponiveis = new Button("Livros disponiveis para empréstimo");
        Button btnEmprestarLivro = new Button("Realizar empréstimo");
        Button btnDevolverLivro = new Button("Devolver um livro emprestado");
        Button btnCadastrarLivro = new Button("Cadastre um novo livro na biblioteca!");
        Button btnCadastrarCliente = new Button("Cadastre um novo cliente");
        Button btnListarClientes = new Button("Clientes cadastrados");

        // Configuração dos eventos dos botões
        btnListarLivros.setOnAction(e -> exibirListaLivros());
        btnListarLivrosDisponiveis.setOnAction(e -> exibirLivrosDisponiveis());
        btnEmprestarLivro.setOnAction(e -> exibirEmprestimo());
        btnDevolverLivro.setOnAction(e -> exibirDevolucao());
        btnCadastrarLivro.setOnAction(e -> exibirCadastroLivro());
        btnCadastrarCliente.setOnAction(e -> exibirCadastroCliente());
        btnListarClientes.setOnAction(e -> exibirListaClientes());

        // Adiciona os botões na ordem desejada
        mainLayout.getChildren().addAll(
                btnListarLivros,
                btnListarLivrosDisponiveis,
                btnEmprestarLivro,
                btnDevolverLivro,
                btnCadastrarLivro,
                btnCadastrarCliente,
                btnListarClientes
        );

        Scene scene = new Scene(mainLayout, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Janela para cadastro de livros
    private void exibirCadastroLivro() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Livro");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblTitulo = new Label("Título:");
        TextField tfTitulo = new TextField();
        Label lblAutor = new Label("Autor:");
        TextField tfAutor = new TextField();
        Label lblGenero = new Label("Gênero:");
        TextField tfGenero = new TextField();

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            String titulo = tfTitulo.getText();
            String autor = tfAutor.getText();
            String genero = tfGenero.getText();
            biblioteca.adicionarLivro(titulo, autor, genero);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Livro Adicionado");
            alert.setHeaderText(null);
            alert.setContentText("Livro adicionado: " + titulo + " por " + autor + " [" + genero + "]");
            alert.showAndWait();

            stage.close();
        });

        grid.add(lblTitulo, 0, 0);
        grid.add(tfTitulo, 1, 0);
        grid.add(lblAutor, 0, 1);
        grid.add(tfAutor, 1, 1);
        grid.add(lblGenero, 0, 2);
        grid.add(tfGenero, 1, 2);
        grid.add(btnSalvar, 1, 3);

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Janela para cadastro de clientes
    private void exibirCadastroCliente() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Cliente");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNome = new Label("Nome:");
        TextField tfNome = new TextField();
        Label lblDataNascimento = new Label("Data Nasc. (dd/MM/yyyy):");
        TextField tfDataNascimento = new TextField();
        Label lblEmail = new Label("Email:");
        TextField tfEmail = new TextField();

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            String nome = tfNome.getText();
            String dataStr = tfDataNascimento.getText();
            String email = tfEmail.getText();
            try {
                LocalDate dataNascimento = LocalDate.parse(dataStr, dtf);
                biblioteca.cadastrarCliente(nome, dataNascimento, email);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente Cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Cliente cadastrado: " + nome + " (" + email + ")");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Data inválida!");
                alert.showAndWait();
            }
            stage.close();
        });

        grid.add(lblNome, 0, 0);
        grid.add(tfNome, 1, 0);
        grid.add(lblDataNascimento, 0, 1);
        grid.add(tfDataNascimento, 1, 1);
        grid.add(lblEmail, 0, 2);
        grid.add(tfEmail, 1, 2);
        grid.add(btnSalvar, 1, 3);

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Janela para listar os livros cadastrados (exibe os livros em um Alert)
    private void exibirListaLivros() {
        List<Livro> livros = biblioteca.getLivros();
        String mensagem;
        if (livros.isEmpty()) {
            mensagem = "Nenhum livro cadastrado.";
        } else {
            mensagem = livros.stream()
                    .map(Livro::toString)
                    .collect(Collectors.joining("\n"));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista de Livros");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    // Janela para listar os livros disponíveis (que não estão emprestados)
    private void exibirLivrosDisponiveis() {
        List<Livro> disponiveis = biblioteca.getLivros()
                .stream()
                .filter(Livro::isDisponivel)
                .collect(Collectors.toList());
        String mensagem;
        if (disponiveis.isEmpty()) {
            mensagem = "Nenhum livro disponível.";
        } else {
            mensagem = disponiveis.stream()
                    .map(Livro::toString)
                    .collect(Collectors.joining("\n"));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Livros Disponíveis");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    // Janela para listar os clientes cadastrados
    private void exibirListaClientes() {
        List<Cliente> clientes = biblioteca.listarClientes();
        String mensagem;
        if (clientes.isEmpty()) {
            mensagem = "Nenhum cliente cadastrado.";
        } else {
            mensagem = clientes.stream()
                    .map(Cliente::toString)
                    .collect(Collectors.joining("\n"));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista de Clientes");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    // Janela para realizar empréstimo de livro
    private void exibirEmprestimo() {
        Stage stage = new Stage();
        stage.setTitle("Realizar Empréstimo");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblClienteId = new Label("ID Cliente:");
        TextField tfClienteId = new TextField();
        Label lblLivroId = new Label("ID Livro:");
        TextField tfLivroId = new TextField();

        Button btnEmprestar = new Button("Emprestar");
        btnEmprestar.setOnAction(e -> {
            try {
                int clienteId = Integer.parseInt(tfClienteId.getText());
                int livroId = Integer.parseInt(tfLivroId.getText());
                biblioteca.emprestarLivro(clienteId, livroId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Empréstimo Realizado");
                alert.setHeaderText(null);
                alert.setContentText("Empréstimo realizado para cliente " + clienteId + " com o livro " + livroId);
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IDs inválidos!");
                alert.showAndWait();
            }
            stage.close();
        });

        grid.add(lblClienteId, 0, 0);
        grid.add(tfClienteId, 1, 0);
        grid.add(lblLivroId, 0, 1);
        grid.add(tfLivroId, 1, 1);
        grid.add(btnEmprestar, 1, 2);

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Janela para registrar a devolução de um livro
    private void exibirDevolucao() {
        Stage stage = new Stage();
        stage.setTitle("Devolver Livro");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblEmprestimoId = new Label("ID Empréstimo:");
        TextField tfEmprestimoId = new TextField();

        Button btnDevolver = new Button("Devolver");
        btnDevolver.setOnAction(e -> {
            try {
                int emprestimoId = Integer.parseInt(tfEmprestimoId.getText());
                biblioteca.devolverLivro(emprestimoId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Livro Devolvido");
                alert.setHeaderText(null);
                alert.setContentText("Livro devolvido no empréstimo de ID " + emprestimoId);
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "ID inválido!");
                alert.showAndWait();
            }
            stage.close();
        });

        grid.add(lblEmprestimoId, 0, 0);
        grid.add(tfEmprestimoId, 1, 0);
        grid.add(btnDevolver, 1, 1);

        Scene scene = new Scene(grid, 350, 150);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
