package jdbcInterface;
import objeto.Usuario;

public interface UsuarioDAO {
	
	public boolean login(Usuario usuario);
	public boolean inserir(Usuario usuario);
	boolean usuarioExistente(Usuario usuario);

}
