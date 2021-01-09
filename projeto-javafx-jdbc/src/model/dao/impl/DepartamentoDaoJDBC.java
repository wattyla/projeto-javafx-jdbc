package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Departamento departamento) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "INSERT INTO coursejdbc.departamento "
					+ "(Name) VALUES (?);",
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, departamento.getName());
			
			int linhasInseridas = preparedStatement.executeUpdate();
			
			if (linhasInseridas > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					departamento.setId(id);
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
	public void update(Departamento departamento) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "UPDATE coursejdbc.departamento "
					+ "SET Name=? "
					+ "WHERE Id = ?;");

			preparedStatement.setString(1, departamento.getName());
			preparedStatement.setInt(2, departamento.getId());
			
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
					+ "DELETE FROM coursejdbc.departamento "
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
	public Departamento consultaPeloId(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		try {
			preparedStatement = conn.prepareStatement(""
					+ "SELECT * "
					+ "FROM coursejdbc.departamento "
					+ "WHERE Id = ?; ");
			preparedStatement.setInt(1, id);
			
			resultado = preparedStatement.executeQuery();
			
			if (resultado.next()) {
				return instanciaDepartamento(resultado);
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
	public List<Departamento> cunsultaTodos() {
		Statement statement = null;
		ResultSet resultado = null;
		try {
			statement = conn.createStatement();
			
			resultado = statement.executeQuery(""
					+ "SELECT *"
					+ "FROM coursejdbc.departamento;");
			
			List<Departamento> listaVendedores = new ArrayList<>();
			
			while (resultado.next()) {
				
				listaVendedores.add(instanciaDepartamento(resultado));
			}
			return listaVendedores;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharResultSet(resultado);
			DB.fecharStatement(statement);
		}
	}
	
	private Departamento instanciaDepartamento(ResultSet resultado) throws SQLException {
		return new Departamento(resultado.getInt("Id"), resultado.getString("Name"));
	}
}
