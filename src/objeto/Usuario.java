package objeto;

public class Usuario {
	private int id;
	private String usuario;
	private String senha;
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}
	public String getUsuario(){
		return this.usuario;
	}
	public void setSenha(String senha){
		this.senha = senha;
	}
	public String getSenha(){
		return this.senha;
	}

}
