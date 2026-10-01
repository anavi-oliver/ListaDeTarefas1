package com.example.prova1.modelo;

import org.springframework.data.repository.CrudRepository; 

//banco de dados: ja da os metodos save(), findAll(), findById(), delete() do crud
public interface TarefaRepository extends CrudRepository<Tarefa, Integer> {
}
