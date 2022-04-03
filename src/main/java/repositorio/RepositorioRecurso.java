package repositorio;

import modelo.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioRecurso extends MongoRepository<Recurso, String> {

    List<Recurso> findByArea(String area);
    List<Recurso> findByTipo(String tipo);
    List<Recurso> findByAreaAndTipo(String area, String tipo);

}