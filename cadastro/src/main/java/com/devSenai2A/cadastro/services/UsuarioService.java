package com.devSenai2A.cadastro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devSenai2A.cadastro.entities.Usuario;
import com.devSenai2A.cadastro.repositories.UsuarioRepository;

@Service

public class UsuarioService {
   
    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; 
   
    public List<Usuario> listarTodos(){
        return repository.findAll();
    }
   
    public Usuario cadastrar(Usuario usuario) {
     // senha criptografada
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return repository.save(usuario);
    }
    
    
    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = repository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }


    	usuario.setNome(dados.getNome());
    	usuario.setEmail(dados.getEmail());
    	usuario.setSenha(dados.getSenha());
    	usuario.setPerfil(dados.getPerfil());
    	usuario.setEndereco(dados.getEndereco());
    	usuario.setBairro(dados.getBairro());
    	usuario.setComplemento(dados.getComplemento());
    	usuario.setCep(dados.getCep());
    	usuario.setCidade(dados.getCidade());
    	usuario.setEstado(dados.getEstado());
    	usuario.setFoto(dados.getFoto());

    	return repository.save(usuario);
    	}

    	public boolean deletar(Long id) {
    	if (!repository.existsById(id)) {
    	return false;
    	}

    	// localhost:8080/usuarios/3 DELETE
    	repository.deleteById(id);
    	return true;
    	}
    	
    	  public Usuario buscarPorId(Long id) {
    	        return repository.findById(id).orElse(null);
    	    }
    	
    	public Usuario login(String email, String senha) {
    		   		
    		Usuario usuario =  repository.findByEmail(email);
    		if (usuario == null) {
    			return null;
    		}
            // Validar senha com bcrypt
            if (!passwordEncoder.matches(senha, usuario.getSenha())) {
                return null;
            }
            return usuario;
    	}
    	
    	@Autowired
    	private JavaMailSender mailSender; 
    	
    	public boolean enviarEmailRecuperacao(String email) {
    		Usuario usuario = repository.findByEmail(email);
    		
    		if (usuario != null) {
    			String link = "http://127.0.0.1:5500/redefinir-senha.html?email=" + email;		
    			SimpleMailMessage mensagem = new SimpleMailMessage();
    			mensagem.setTo(email);
    			mensagem.setSubject("Recuperação de Senha - DevSenai");
    			mensagem.setText(
    					"Olá " + usuario.getNome() + ",\n\n" + "Clique no link abaixo para criar uma nova senha:\n" +
    			link
    		  );
    				
    	mailSender.send(mensagem);
    			
    			System.out.println("Email enviado para:" +email );
    			
    			return true;
    		}
    		
    		return false;
    		}
    	
    	public boolean redefinirSenha(String email, String novaSenha) {
    		Usuario usuario = repository.findByEmail(email);
    		
    		if( usuario != null) {
    			usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
    			repository.save(usuario);
    			return true;
    		}
    		return false;
    	}    	
}
