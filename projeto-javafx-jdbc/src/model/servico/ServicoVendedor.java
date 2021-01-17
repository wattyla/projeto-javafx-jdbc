package model.servico;

import java.util.List;

import model.dao.FabricaDao;
import model.dao.VendedorDao;
import model.entidades.Vendedor;

public class ServicoVendedor {
	VendedorDao vendedorDao = FabricaDao.criaVendedorDao();
	
	public List<Vendedor> retornaTodosVendedores(){
		return vendedorDao.cunsultaTodos();
	}
	
	public void insereOuAtualizaVendedor(Vendedor vendedor) {
		if (vendedor.getId() == null) {
			vendedorDao.insert(vendedor);
		}else {
			vendedorDao.update(vendedor);
		}
	}
}
