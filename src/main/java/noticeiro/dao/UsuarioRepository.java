package noticeiro.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import noticeiro.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	public Usuario findByUsername(String username);
}
