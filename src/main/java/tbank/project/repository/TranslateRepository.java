package tbank.project.repository;

import org.springframework.data.repository.ListCrudRepository;
import tbank.project.entity.Translator;

import java.util.List;
import java.util.Optional;

public interface TranslateRepository {
    void save(Translator translator);

    List<Translator> findAll();

    Translator findById(Long id);

    List<Translator> findByIp(String title);
}
