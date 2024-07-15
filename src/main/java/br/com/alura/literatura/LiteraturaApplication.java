package br.com.alura.literatura;
import br.com.alura.literatura.principal.Principal;
import br.com.alura.literatura.repositorio.AutorRepositorio;
import br.com.alura.literatura.repositorio.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	LivroRepositorio livroRepositorio;

	@Autowired
	AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livroRepositorio, autorRepositorio);
		principal.exibeMenu();
	}
}