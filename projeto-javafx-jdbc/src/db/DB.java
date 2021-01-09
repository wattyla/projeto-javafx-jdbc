package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class DB {
	
	private static Connection conexaoBanco = null;
	
	/*
	 * Realiza a conexão com o banco de dados.
	 */
	public static Connection getConnection() {
		if(conexaoBanco == null) {
			try {
				Properties properties = carregarPropriedadesBancoMySQL();
				String urlBanco = properties.getProperty("dburl");
				conexaoBanco = DriverManager.getConnection(urlBanco, properties);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conexaoBanco;
	}
	
	/*
	 * Metodo responsavel de realizar o fechamento da conexão com o banco de dados
	 */
	public static void fechaConexaoBanco() {
		if (conexaoBanco != null) {
			try {
				conexaoBanco.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	/*
	 * Metodo para caregar as proprietades que estão descritas no arquivo db.properties
	 */
	private static Properties carregarPropriedadesBancoMySQL() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void fecharStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void fecharResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
