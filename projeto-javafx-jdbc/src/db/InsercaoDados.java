package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InsercaoDados {
	public void insercaoDados() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			conn = DB.getConnection();
			
			preparedStatement = conn.prepareStatement(
					"INSERT INTO vendedor "
					+ "(Name, Email, DataNascimento, SalarioBase, DepartamentoId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, "wattyla");
			preparedStatement.setString(2, "wattylaa@hotmail.com");
			preparedStatement.setDate(3, new java.sql.Date(sdf.parse("13/10/1989").getTime()));
			preparedStatement.setDouble(4, 3000.0);
			preparedStatement.setInt(5, 4);
			
			int linhasAlteradas = preparedStatement.executeUpdate();
			
			if (linhasAlteradas > 0) {
				ResultSet chavesInseridas = preparedStatement.getGeneratedKeys();
				while (chavesInseridas.next()) {
					System.out.println("Numero do ID:"+chavesInseridas.getInt(1));
				}
			}else {
				System.out.println("Nenhuma linha alterada");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}finally {
			DB.fecharStatement(preparedStatement);
			DB.getConnection();
		}
	}
}
