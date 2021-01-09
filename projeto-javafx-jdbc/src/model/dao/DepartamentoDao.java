package model.dao;

import java.util.List;

import model.entidades.Departamento;

public interface DepartamentoDao {
	void insert(Departamento obj);
	void update(Departamento obj);
	void deletarPeloId(Integer id);
	Departamento consultaPeloId(Integer id);
	List<Departamento> cunsultaTodos();
	
}
