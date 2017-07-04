package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conexao.Conexao;
import jdbc.JDBCUsuarioDAO;
import objeto.Usuario;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Usuario usuario = new Usuario();
		try{
			//validação
			usuario.setUsuario(request.getParameter("usuario"));
			String senha = request.getParameter("senha");
			//Convertendo o campo em uma String MD5
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(senha.getBytes(),0,senha.length());
			String hash = new BigInteger(1,m.digest()).toString(16);
			System.out.println("MD5: "+hash);
			usuario.setSenha(hash);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			boolean login = jdbcUsuario.login(usuario);
			if(login){
				HttpSession sessao = request.getSession();
				sessao.setAttribute("login", request.getParameter("usuario"));
				response.sendRedirect("/loginUser/resources/login/cadastrarUsuario.html");
			} else{
				response.sendRedirect("/loginUser/index.html");

			}
		} catch (Exception e) {
			System.out.println("Erro no login.java"+e);
			e.printStackTrace();
		}
			
		    }

	}

