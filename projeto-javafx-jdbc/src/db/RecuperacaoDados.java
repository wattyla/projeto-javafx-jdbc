package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecuperacaoDados {
	
	public void recuperaDadosBanco() {
		Connection connection = null;
		Statement servicoCriacaoComandos = null;
		ResultSet resultadoBuscas = null;
		try {
			connection = DB.getConnection();
			servicoCriacaoComandos = connection.createStatement();
			resultadoBuscas = servicoCriacaoComandos.executeQuery(""
					+ "SELECT * FROM coursejdbc.departamento;");
			
			while (resultadoBuscas.next()) {
				System.out.println(resultadoBuscas.getInt("Id")+","+resultadoBuscas.getString("Name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.fecharResultSet(resultadoBuscas);
			DB.fecharStatement(servicoCriacaoComandos);
			DB.fechaConexaoBanco();
		}
	}
}
