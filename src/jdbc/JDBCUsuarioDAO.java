package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import objeto.Usuario;
import jdbcInterface.UsuarioDAO;

public class JDBCUsuarioDAO implements UsuarioDAO {
	private Connection conexao;
	
	public JDBCUsuarioDAO(Connection conexao){
		this.conexao = conexao;
	}
	
	@Override
	public boolean login(Usuario usuario) {
		try{
String comando = "select * from usuario where usuario = ? and senha = ? ";
			java.sql.PreparedStatement stmt = conexao.prepareStatement(comando);
			stmt.setString(1, usuario.getUsuario());
			stmt.setString(2, usuario.getSenha());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			System.out.println("Erro JDBCUsuarioDAO método Login: "+e);
			return false;
		}
	}
	
	@Override
	public boolean usuarioExistente(Usuario usuario){
		try{
			String comando ="select * from usuario where usuario = ?";

			java.sql.PreparedStatement stmt = conexao.prepareStatement(comando);
			stmt.setString(1, usuario.getUsuario());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){		
				return true;
			}else{
				return false;						
			}
		}catch(Exception e){
			System.out.println("Erro JDBCUsuarioDAO método usuarioExistente: "+e);
			return false;
		}
	}

	@Override
	public boolean inserir(Usuario usuario) {
		String comando = "insert into usuario"+
				"(usuario, senha)"+
				"values(?,?)";
		System.out.println(usuario);
		java.sql.PreparedStatement p;
		
		try{
			p = this.conexao.prepareStatement(comando);
			p.setString(1, usuario.getUsuario());
			p.setString(2, usuario.getSenha());
			p.execute();
		} catch(SQLException e){
			System.out.println("Erro em JDBCUsuasrioDAO inserir : "+e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

