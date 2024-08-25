package tbank.project.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "translator")
@Data
public class Translator {

    @Id
    private Long id;

    @Column
    private String ip;

    @Column
    private String request;

    @Column
    private String answer;
}
