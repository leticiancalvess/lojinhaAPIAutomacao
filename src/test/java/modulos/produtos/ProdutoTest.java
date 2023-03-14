package modulos.produtos;


import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do módulo do produto") //anotação que descreve o que são os testes
public class ProdutoTest {
    private String token; //atributo privado da classe pq precisa do token em todos os métodos
    @BeforeEach //antes de cada teste
    public void beforeEach() {
        //configurando os dados da api rest da lojinha
        baseURI = "http://165.227.93.41"; //é o mesmo que RestAssured.baseURI, mas como importou de forma estática, o intelliJ
        //entende que é o RestAssured e aí precisa passar apenas o baseURI.
        basePath = "/lojinha"; //caminho inicial da aplicação
        //obter o token do usuário admin
         this.token = given()
                .contentType(ContentType.JSON)  //com a ajuda do jackson-databind é possível converter o objeto usuario em json
                .body(UsuarioFactory.criarUsuario("admin", "admin"))
            .when() //qual o método que vai usar
                .post("/v2/login")
            .then() //o que quer que aconteça dps de enviar a requisição
                .extract() //extrai um valor que vem na resposta
            .path("data.token");
    }
    @Test
    @DisplayName("Validar que o valor do produto igual a 0.00 não é permitido")
    public void testValidarLimitesZeradoProibidoValorProduto() {

        //tentar inserir um produto com o valor 0.00 e validar que a mensagem de erro foi apresentada e o
        //status code retornado foi 422

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
    @Test
    @DisplayName("Validar que o valor do produto superior a 7000.00 não é permitido")
    public void testValidarLimitesAcimaProibidoValorProduto() {

        //tentar inserir um produto com o valor 0.00 e validar que a mensagem de erro foi apresentada e o
        //status code retornado foi 422

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
                .when()
                .post("/v2/produtos")
                .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);

    }
}
