package model.servico;

import java.util.List;

import model.dao.DepartamentoDao;
import model.dao.FabricaDao;
import model.entidades.Departamento;

public class ServicoDepartamento {
	DepartamentoDao departamentoDao = FabricaDao.criaDepartamentoDao();
	
	public List<Departamento> retornaTodosDeparamentos(){
		return departamentoDao.cunsultaTodos();
	}
	
	public void insereOuAtualizaDepartamento(Departamento departamento) {
		if (departamento.getId() == null) {
			departamentoDao.insert(departamento);
		}else {
			departamentoDao.update(departamento);
		}
	}
}
