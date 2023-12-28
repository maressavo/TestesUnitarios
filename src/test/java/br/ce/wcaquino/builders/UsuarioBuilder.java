package br.ce.wcaquino.builders;

import br.ce.wcaquino.entity.Usuario;

public class UsuarioBuilder {
    private Usuario usuario;

    private UsuarioBuilder() {

    }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.usuario = new Usuario();
        builder.usuario.setNome("Maria das Graças");
        return builder;
    }

    public Usuario agora() {
        return usuario;
    }
}
