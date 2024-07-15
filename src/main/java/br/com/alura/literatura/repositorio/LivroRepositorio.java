package br.com.alura.literatura.repositorio;
import br.com.alura.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepositorio extends JpaRepository<Livro, Long>{
    @Query("SELECT i FROM Livro i WHERE i.idioma LIKE %:idiomaEscolhido%")
    List<Livro> findAllByIdioma(String idiomaEscolhido);
}
