package dataFactory;

import pojo.CadastrarUsuarioPojo;

public class CadastrarUsuarioFactory {

    public static CadastrarUsuarioPojo cadastrarNovoUsuario(String nome, String login, String senha) {
         CadastrarUsuarioPojo cadastrar = new CadastrarUsuarioPojo();
         cadastrar.setUsuarioNome(nome);
         cadastrar.setUsuarioLogin(login);
         cadastrar.setUsuarioSenha(senha);

         return cadastrar;
    }
}
