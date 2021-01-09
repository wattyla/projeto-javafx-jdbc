package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {
	
	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Vendedor vendedor) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "INSERT INTO coursejdbc.vendedor "
					+ "(Name, Email, DataNascimento, SalarioBase, DepartamentoId) "
					+ "VALUES "
					+ "(?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, vendedor.getName());
			preparedStatement.setString(2, vendedor.getEmail());
			preparedStatement.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
			preparedStatement.setDouble(4, vendedor.getSalarioBase());
			preparedStatement.setInt(5, vendedor.getDepartamento().getId());
			
			int linhasInseridas = preparedStatement.executeUpdate();
			
			if (linhasInseridas > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					vendedor.setId(id);
				}
				DB.fecharResultSet(resultSet);
			}else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharStatement(preparedStatement);
		}	
	}

	@Override
	public void update(Vendedor vendedor) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "UPDATE coursejdbc.vendedor "
					+ "SET Name=?, Email=?, DataNascimento=?, SalarioBase=?, DepartamentoId=? "
					+ "WHERE Id = ?;");

			preparedStatement.setString(1, vendedor.getName());
			preparedStatement.setString(2, vendedor.getEmail());
			preparedStatement.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
			preparedStatement.setDouble(4, vendedor.getSalarioBase());
			preparedStatement.setInt(5, vendedor.getDepartamento().getId());
			preparedStatement.setInt(6, vendedor.getId());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharStatement(preparedStatement);
		}			
	}

	@Override
	public void deletarPeloId(Integer id) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "DELETE FROM coursejdbc.vendedor "
					+ "WHERE Id = ?;");

			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharStatement(preparedStatement);
		}			
	}

	@Override
	public Vendedor consultaPeloId(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "SELECT t1.*, t2.Name as DepName"
					+ " FROM coursejdbc.vendedor as t1,"
					+ " coursejdbc.departamento as t2"
					+ " WHERE t1.DepartamentoId = t2.Id and"
					+ "	   t1.Id = ?; ");
			preparedStatement.setInt(1, id);
			
			resultado = preparedStatement.executeQuery();
			if (resultado.next()) {
				Departamento departamento = instanciaDepartamento(resultado);
				Vendedor vendedor = instanciaVendedor(resultado,departamento);
				return vendedor;
			}
			return null;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharResultSet(resultado);
			DB.fecharStatement(preparedStatement);
		}
	}

	@Override
	public List<Vendedor> cunsultaTodos() {
		Statement statement = null;
		ResultSet resultado = null;
		try {
			statement = conn.createStatement();
			
			resultado = statement.executeQuery(""
					+ "SELECT t1.*, t2.Name as DepName "
					+ "FROM coursejdbc.vendedor as t1,"
					+ "coursejdbc.departamento as t2 "
					+ "where t1.DepartamentoId = t2.Id;");
			
			List<Vendedor> listaVendedores = new ArrayList<>();
			Map<Integer,Departamento> map = new HashMap<>();
			
			while (resultado.next()) {
				Departamento departamento = map.get(resultado.getInt("DepartamentoId"));
				
				if  (departamento == null) {
					departamento = instanciaDepartamento(resultado);
					map.put(resultado.getInt("DepartamentoId"), departamento);
				}
				
				listaVendedores.add(instanciaVendedor(resultado,departamento));
			}
			return listaVendedores;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharResultSet(resultado);
			DB.fecharStatement(statement);
		}
	}

	@Override
	public List<Vendedor> cunsultaPeloDepartamento(Departamento departamento) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "SELECT t1.*, t2.Name as DepName "
					+ "FROM coursejdbc.vendedor as t1,"
					+ "coursejdbc.departamento as t2 "
					+ "where t1.DepartamentoId = t2.Id and "
					+ "t2.Id = ? and "
					+ "t2.Name = ? "
					+ "order by t1.Name;");

			preparedStatement.setInt(1, departamento.getId());
			preparedStatement.setString(2, departamento.getName());
			
			resultado = preparedStatement.executeQuery();
			List<Vendedor> listaVendedores = new ArrayList<>();
			
			while (resultado.next()) {
				listaVendedores.add(instanciaVendedor(resultado,departamento));
			}
			return listaVendedores;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharResultSet(resultado);
			DB.fecharStatement(preparedStatement);
		}
		
	}
	
	@Override
	public List<Vendedor> cunsultaPeloIdDepartamento(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "SELECT t1.*, t2.Name as DepName "
					+ "FROM coursejdbc.vendedor as t1,"
					+ "coursejdbc.departamento as t2 "
					+ "where t1.DepartamentoId = t2.Id and "
					+ "t2.Id = ? "
					+ "order by t1.Name;");

			preparedStatement.setInt(1, id);
			
			resultado = preparedStatement.executeQuery();
			List<Vendedor> listaVendedores = new ArrayList<>();
			Map<Integer,Departamento> map = new HashMap<>();
			
			while (resultado.next()) {
				Departamento departamento = map.get(resultado.getInt("DepartamentoId"));
				
				if  (departamento == null) {
					departamento = instanciaDepartamento(resultado);
					map.put(resultado.getInt("DepartamentoId"), departamento);
				}
				
				listaVendedores.add(instanciaVendedor(resultado,departamento));
			}
			return listaVendedores;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharResultSet(resultado);
			DB.fecharStatement(preparedStatement);
		}
	}
	
	private Departamento instanciaDepartamento(ResultSet resultado) throws SQLException {
		return new Departamento(resultado.getInt("DepartamentoId"), resultado.getString("DepName"));
	}

	private Vendedor instanciaVendedor(ResultSet resultado, Departamento departamento) throws SQLException {
		return new Vendedor(resultado.getInt("Id"), resultado.getString("Name"), 
				resultado.getString("Email"),resultado.getDate("DataNascimento"),
				resultado.getDouble("SalarioBase"), departamento);
	}



}
