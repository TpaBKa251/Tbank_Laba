package tbank.project.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tbank.project.entity.Translator;
import tbank.project.repository.TranslateRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TranslateRepositoryImpl implements TranslateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Translator translator) {
        if (jdbcTemplate.queryForObject("select count(*) from translator where id = " + translator.getId(), Integer.class) == 0){
            String sql = "insert into translator (ip, request, answer) values ('" + translator.getIp() + "', '" + translator.getRequest() + "', '" + translator.getAnswer() + "')";
            jdbcTemplate.execute(sql);
        }
        else {
            String sql = "update translator set ip = '" + translator.getIp() + "', request = '" + translator.getRequest() + "', answer = '" + translator.getAnswer() + "' where id = " + translator.getId();
            jdbcTemplate.execute(sql);
        }
    }

    @Override
    public List<Translator> findAll() {
        return List.of();
    }

    @Override
    public Translator findById(Long id) {
        Translator translator = new Translator();

        translator.setId(id);

        String sql = "select ip from translator where id = " + id;
        String ip = jdbcTemplate.queryForObject(sql, String.class);
        translator.setIp(ip);

        sql = "select answer from translator where id = " + id;
        String answer = jdbcTemplate.queryForObject(sql, String.class);
        translator.setAnswer(answer);

        sql = "select request from translator where id = " + id;
        String request = jdbcTemplate.queryForObject(sql, String.class);
        translator.setRequest(request);

        return translator;
    }

    @Override
    public List<Translator> findByIp(String title) {
        return List.of();
    }
}
