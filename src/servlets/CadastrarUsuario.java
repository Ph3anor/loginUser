package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.awt.PageAttributes.OriginType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

import objeto.Usuario;
import conexao.Conexao;
import jdbc.JDBCUsuarioDAO;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class CadastrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	public CadastrarUsuario(){
//		super();
//	}
		
	@SuppressWarnings("null")
	private void process(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		Usuario usuario = new Usuario();
		
		try{

			
			usuario.setUsuario(request.getParameter("usuario"));
			String senha = request.getParameter("senha");
			
			//validação MD5
//			//Convertendo o campo em uma String MD5
//			MessageDigest m=MessageDigest.getInstance("MD5");
//			byte messageDigest []= m.digest(senha.getBytes("UTF-8"));
//			StringBuilder hexString = new StringBuilder();
//			for (byte b : messageDigest){
//				hexString.append(String.format("%02X", 0xFF & b));
//			}
//			senha = hexString.toString();
			//SHA
			
			MessageDigest m = MessageDigest.getInstance("SHA-256");
			byte messageDigest [] = m.digest(senha.getBytes("UTF-8"));
			
					StringBuilder hexString = new StringBuilder();
					for (byte b : messageDigest) {
						hexString.append(String.format("%02X", 0xFF & b));
					}
					senha = hexString.toString();
			
			usuario.setSenha(senha);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			boolean usuarioExistente = jdbcUsuario.usuarioExistente(usuario);
			if(!usuarioExistente){
				boolean retorno = jdbcUsuario.inserir(usuario);
				if(retorno){
					Map<String, String>msg = new HashMap<String, String>();
					msg.put("msg","Usuario cadastrado com sucesso.");
					
					String json = new Gson().toJson(msg);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				}else{
					response.setStatus(500);
					throw new IllegalArgumentException("Falha no cadastro de um usuário");
				}
			}else{
				Map<String, String>msg = new HashMap<String, String>();
				msg.put("msg","Usuario já cadastrado no sistema.");
				
				String json = new Gson().toJson(msg);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
			conec.fecharConexao();
			
		}catch (Exception e){
			Map<String, String>msg = new HashMap<String, String>();
			msg.put("msg", e.getMessage());
			
			String json = new Gson().toJson(msg);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		process(request, response);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		process(request, response);
	}
}
