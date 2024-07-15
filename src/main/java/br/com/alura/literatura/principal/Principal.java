package br.com.alura.literatura.principal;
import br.com.alura.literatura.dto.LivroDTO;
import br.com.alura.literatura.dto.ResultsDTO;
import br.com.alura.literatura.model.Autor;
import br.com.alura.literatura.model.Livro;
import br.com.alura.literatura.repositorio.AutorRepositorio;
import br.com.alura.literatura.repositorio.LivroRepositorio;
import br.com.alura.literatura.api.ConsumoAPI;
import br.com.alura.literatura.service.ConverterDados;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI api = new ConsumoAPI();
    private ConverterDados conversor = new ConverterDados();
    private final String endereco = "http://gutendex.com/books/?search=";
    private List<Autor> autores;
    private List<Livro> livros;

    private LivroRepositorio livroRepositorio;
    private AutorRepositorio autorRepositorio;

    public Principal(LivroRepositorio livroRepositorio, AutorRepositorio autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("Escolha uma das opções abaixo: ");
            var menu = """
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmUmDeterminadoAno();
                    break;
                case 5:
                    livrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saido...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Insira o nome do livro: ");
        var buscaLivroPorNome = scanner.nextLine();
        var json = api.consumirApi(endereco + buscaLivroPorNome.replace(" ", "%20"));

        LivroDTO dadosLivros = conversor.obterDados(json, LivroDTO.class);

        if (dadosLivros.resultados() != null && !dadosLivros.resultados().isEmpty()) {
            ResultsDTO livroBuscado = dadosLivros.resultados().get(0);
            Livro livro = new Livro(livroBuscado);
            System.out.println(livro);

            livroRepositorio.save(livro);
        } else {
            System.out.println("Nenhum livro encontrado.");
        }

    }

    private void listarLivrosRegistrados() {
        livros = livroRepositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.println("Insira o ano que deseja pesquisar:");
        var ano = scanner.nextInt();
        autores = autorRepositorio.findAllByAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void livrosPorIdioma() {
        System.out.println("""
                Digite o idioma que deseja escolher:
                Pt - Português
                En - Inglês
                Es - Espanhol
                Fr - Francês
                """);
        var idiomaEscolhido = scanner.nextLine();
        livros = livroRepositorio.findAllByIdioma(idiomaEscolhido);

        if (livros.isEmpty()) {
            System.out.println("Idioma não encontrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }
}

