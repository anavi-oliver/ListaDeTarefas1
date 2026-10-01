//tarefa.java = faz as entidades, chaves, estabelece os caracteres proibidos do regexp validation, as variaveis, 
// add as mensagens de erro pelos metodos validation, getters e setters

package com.example.prova1.modelo;

//persistencia !!
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//validation -> obs pattern:
import jakarta.validation.constraints.FutureOrPresent; //se nao for uma data no futuro ou presente entao nao pode
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern; //pattern: se string for igual a regexp, entao mostra a mensagem 
import jakarta.validation.constraints.Size;

//data pra saber se é do passado, ao inves de ficar atualizando em tempo real para ver se a data ja foi
import org.springframework.format.annotation.DateTimeFormat; //padrao da data
import java.util.Date; 


                                    /////////////////////////////////////////////////////////////// 

@Entity
@Table(name = "tarefa") //entidade = tarefa, entidade vira uma tabela no banco

public class Tarefa { 

private static final String PROIBIDO = "^[\\p{L}\\p{N} .,!?()'-]*$"; //os caracteres proibidos
// esse \p{L} é qqr letra c acento e o \p{N} é qualquer numero

    //id/ chave primaria 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //quem gera o valor é o próprio banco (auto incremento)
    private int id; 

    //titulo
    @NotBlank(message = "O título é obrigatório") //nao pode ficavazio
    @Size(max = 100, message = "O título pode ter no máximo 100 caracteres") //tamanho maximo = 100
    @Pattern(regexp = PROIBIDO, message = "O título contém caracteres não permitidos") //so aceita os caracteres que a regex permite

    private String titulo; 

    //descricao
    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    @Pattern(regexp = PROIBIDO, message = "A descrição contém caracteres não permitidos")
    private String descricao;
    private boolean concluida = false;

    //data
    @NotNull(message = "O prazo é obrigatório")
    @FutureOrPresent(message = "O prazo não pode ser uma data no passado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date prazo;

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() { 
        return titulo;
    }
    public void setTitulo(String titulo) {
         this.titulo = titulo; 
        }

    public String getDescricao() { 
        return descricao;
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }

    public boolean isConcluida() {
        return concluida;
    }
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
        }

    public Date getPrazo() { 
        return prazo;
    }
    public void setPrazo(Date prazo) { 
        this.prazo = prazo; 
    }
}

