// ======================= CONTROLLER no spring =======================
        //controller -> requisicoes https
        //inversao de controle:
        //o spring ta no controle, esperando as reqwuisicoes http chegarem (tipo como se fosse o main q chama as fucnoes), 
        // qnd um evento bate c o mapeamento vc nao instancia nem chama nada manualm,ente, so declara q qnd acontecer algo vai rolar 
        
        // padrão MVC: o Controller decide o quê fazer e qual view mostrar; 
        // a View (o .html) decide como aquilo aparece; 
        // o Model é o dado que passa de um pro outro (no caso desse método, um Tarefa vazio).
package com.example.prova1.controle;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.prova1.modelo.Tarefa;
import com.example.prova1.modelo.TarefaRepository;


@Controller 
public class TarefaController {

    @Autowired //pede pro Spring te entregar uma instância pronta, sem terq dar new, 
    TarefaRepository tarefaRepositorio; 
      //o Spring gera uma classe escondida que implementa TarefaRepository, sabendo transformar .save(), .findAll(), .delete()
      //em SQL de verdade pro banco que você configurou.


//======================= MAPEAMENTO =======================
        /* funcoes de mapeamento igual do tutorial
        mapping: liga uma URL + verbo HTTP a um método java; funcoes:
        - get: qnd vc so quer ver uma página/ digitou a URL, clicou num link 
        - post: qnd vc ta enviando dados/aperto "Enviar" num formul
        return nova-tarefa: proj tem dependencia Thymeleaf= Spring procura dentro de src/main/resources/templates/ 
        por um arquivo chamado nova-tarefa.html, renderizar ele (substituindo os th: pelo conteúdo real) e mandar o HTML final de volta pro navegador.
        e daria erro se arquivo chamado não existisse (q n fossem nova-tarefa ou index)
        */

    @GetMapping("/nova-tarefa") // igual toda vez que alguém fizer uma requisição GET para http://localhost:8080/nova-tarefa, chame este método aqu
    public String mostrarFormNovaTarefa(Tarefa tarefa) {
        return "nova-tarefa";
    }

    @PostMapping("/adicionar-tarefa")
    public String adicionarTarefa(@Valid Tarefa tarefa, BindingResult result) { //@valid manda o Spring rodar as validações da classe antes de entrar no método

        if (result.hasErrors()) {
            return "nova-tarefa";
        }
        tarefaRepositorio.save(tarefa);
        return "redirect:/index";
    }

    @GetMapping(value = {"/index", "/"})
    public String mostrarListaTarefas(Model model) {
        model.addAttribute("tarefas", tarefaRepositorio.findAll());
        return "index";
    }

    //pproathvariable pega um pedaço da URL (o {id}) e passa como parâmetro
    @GetMapping("/concluir/{id}")
    public String marcarConclusao(@PathVariable("id") int id) { 
        Tarefa tarefa = tarefaRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("O id da tarefa é inválido: " + id));
        //objeto tarefa= procuraid, se nao tiver, tarefa com id invalido

        tarefa.setConcluida(!tarefa.isConcluida());
        tarefaRepositorio.save(tarefa); //salva nova tarefa na lista
        return "redirect:/index";
    }
    // Tarefa tarefa no nova-tarefa.html = th:object="${tarefa}"

    @GetMapping("/remover/{id}")
    public String removerTarefa(@PathVariable("id") int id) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("O id da tarefa é inválido: " + id));

        tarefaRepositorio.delete(tarefa);
        return "redirect:/index";
    }
}

