package model.dao;

import java.util.List;

import model.entidades.Departamento;
import model.entidades.Vendedor;

public interface VendedorDao {
	
	void insert(Vendedor obj);
	void update(Vendedor obj);
	void deletarPeloId(Integer id);
	Vendedor consultaPeloId(Integer id);
	List<Vendedor> cunsultaTodos();
	List<Vendedor> cunsultaPeloDepartamento(Departamento departamento);
	List<Vendedor> cunsultaPeloIdDepartamento(Integer id);
}
