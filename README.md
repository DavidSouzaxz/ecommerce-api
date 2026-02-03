# venda-api

API RESTful para gerenciamento de vendas e catálogo multi-tenant, escrita em Java com Spring Boot. Projeto organizado para ser simples de executar, extensível e preparado para desenvolvimento local em Windows.

## Stack
- Linguagem: Java 17+
- Framework: Spring Boot (Web, Security)
- Build: Maven (com `mvnw.cmd` para Windows)
- Banco de dados: configurável via `src/main/resources/application.properties`
- IDE: IntelliJ IDEA
- Plataforma alvo: Windows (comandos e exemplos incluídos)

## Principais funcionalidades
- Autenticação e autorização (JWT)
- Gestão de produtos por tenant
- Gestão de pedidos e itens
- Inicialização de dados para ambiente de desenvolvimento

## Estrutura do projeto
- Código-fonte: `src/main/java/com/lebvil/commerce/venda_api`
- Recursos: `src/main/resources`
- Testes: `src/test/java`
- Build output: `target/` (ignorado)
- Diretórios principais:
    - `controller` — endpoints REST
    - `entitys` — entidades JPA / models
    - `repository` — interfaces de persistência
    - `services` — lógica de negócio
    - `config` — configurações (segurança, CORS, MVC)

## Endpoints principais (resumo)
- Autenticação
    - `POST /api/auth/login` — autenticar e obter token JWT
- Produtos
    - `GET /api/products/tenant/{tenant}` — listar produtos do tenant
    - `GET /api/products/tenant/{tenant}/{id}` — obter produto por id
    - `GET /api/products/tenant/{tenant}/category/{categoria}` — listar por categoria
- Pedidos
    - `POST /api/orders` — criar pedido
    - `GET /api/orders/{id}` — obter pedido
- Tenants
    - `GET /api/tenants` — listar tenants
    - `GET /api/tenants/{id}` — obter tenant

Observação: alguns endpoints exigem cabeçalho `Authorization: Bearer <token>`.

## Como rodar (Windows)
1. Compilar e empacotar:
    - `mvnw.cmd clean package`
2. Rodar com Spring Boot:
    - `mvnw.cmd spring-boot:run`
      ou executar o JAR gerado:
    - `java -jar target\venda-api-<versão>.jar`
3. Executar testes:
    - `mvnw.cmd test`

## Configuração
- Edite `src/main/resources/application.properties` ou utilize variáveis de ambiente para:
    - `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
    - propriedades do JWT (chave, validade)
    - `server.port` e perfis (`spring.profiles.active`)

## .gitignore recomendado (resumo)
- Ignorar diretórios e arquivos gerados:
    - `target/`
    - `.idea/`
    - `.mvn/` (se desejar)
    - arquivos de keystore e certificados
    - `src/main/resources/application.properties` (usar `application.properties.example` no repositório)
    - arquivos de logs locais

## Segurança e boas práticas
- Nunca commitar chaves secretas ou credenciais. Use variáveis de ambiente ou serviços de secret management.
- Criar `application.properties.example` com placeholders.
- Incluir testes automatizados e configurar CI para builds.

## Contribuição
- Abra uma issue descrevendo bug/feature.
- Crie branch com nome claro e envie pull request.
- Inclua testes para novas funcionalidades e documentação mínima.

## Licença
- Licença sugerida: MIT — adicionar `LICENSE` conforme necessidade.
