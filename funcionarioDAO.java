package pacoteex2

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.funcionario;

public class funcionarioDAO extends DAO {
	
	public funcionarioDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Funcionario funcionario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO funcionario (codigo, login, senha, sexo) "
				       + "VALUES ("+funcionario.getCodigo()+ ", '" + funcionario.getLogin() + "', '"  
				       + funcionario.getSenha() + "', '" + funcionario.getSexo() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public funcionario get(int codigo) {
		funcionario funcionario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 funcionario = new funcionario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return funcionario;
	}
	
	
	public List<funcionario> get() {
		return get("");
	}

	
	public List<funcionario> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<funcionario> getOrderByLogin() {
		return get("login");		
	}
	
	
	public List<funcionario> getOrderBySexo() {
		return get("sexo");		
	}
	
	
	private List<funcionario> get(String orderBy) {	
	
		List<funcionario> funcionarios = new ArrayList<funcionario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM funcionario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	funcionario u = new funcionario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            funcionarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return funcionarios;
	}


	public List<funcionario> getSexoMasculino() {
		List<funcionario> funcionarios = new ArrayList<funcionario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM funcionario WHERE funcionario.sexo LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	funcionario u = new funcionario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            funcionarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return funcionarios;
	}
	
	
	public boolean update(funcionario funcionario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE funcionario SET login = '" + funcionario.getLogin() + "', senha = '"  
				       + funcionario.getSenha() + "', sexo = '" + funcionario.getSexo() + "'"
					   + " WHERE codigo = " + funcionario.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM funcionario WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String login, String senha) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM funcionario WHERE login LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}