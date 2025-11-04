# Cariopoly

Este repositório contém uma implementação do jogo estilo Monopoly (nome: *Cariopoly*). O projeto está dividido em duas partes principais:

- `backend/` — API em Spring Boot (Java + Maven) que contém a lógica de domínio, casos de uso e infra‑estrutura.
- `frontend/` — cliente web em Vite + React + TypeScript para interação com o jogo.

Este README explica a arquitetura do projeto, descreve algumas das entidades principais, e contém instruções para rodar o ambiente de desenvolvimento (localmente e com Docker), além de diretrizes básicas para CI/CD com GitHub Actions.

---

Sumário
- Visão geral
- Arquitetura (Hexagonal / Clean Architecture)
- Entidades e repositórios
- Ambiente de desenvolvimento (prerequisitos e comandos)
- Docker e Docker Compose para desenvolvimento
- Testes e cobertura (JaCoCo)
- CI/CD e GitHub Actions (pipeline sugerido)
- Git Flow do projeto

---

Visão geral

Cariopoly é uma versão didática do jogo Monopoly com foco em modelar domínio de jogo por meio de boas práticas arquiteturais. O backend contém o núcleo de domínio (casos de uso, entidades) e adaptadores de infra (repositórios em memória e conectores para DB). O frontend é uma aplicação React que consome a API REST do backend.

Arquitetura — Hexagonal e Clean Architecture

O projeto segue princípios da Arquitetura Hexagonal (Ports & Adapters) e está fortemente inspirado em Clean Architecture:

- Núcleo (Domain): contém as entidades (por exemplo `Jogo`, `Jogador`, `Tabuleiro`, `Casa` e especializações como `Propriedade` e `Prisao`) e as regras de negócio puras.
- Use Cases / Application: casos de uso que orquestram lógica entre entidades e portas (por exemplo `iniciarUmJogo`).
- Ports: interfaces (por exemplo `JogoRepository`) que definem contratos para persistência/consulta.
- Adapters / Infra: implementações concretas dos ports — por exemplo `InMemoryJogoRepository` (adaptador para testes/demonstração) e, potencialmente, um adaptador JPA para Postgres.

Benefícios do padrão adotado:
- Separação clara entre regras de negócio e detalhes de infra‑estrutura.
- Testabilidade: núcleo pode ser testado com repositórios em memória ou mocks.
- Flexibilidade para trocar armazenamento (in memory, Postgres, etc.) sem afetar o domínio.

Entidades principais (exemplos encontrados no código)

Durante a análise do backend foram identificados os seguintes artefatos importantes:

- `Jogo` (backend/src/main/java/.../core/domain/entity/Jogo.java)
  - Representa uma partida. Mantém o estado do jogo, jogadores, turno atual e regras aplicáveis.

- `Jogador` (backend/src/main/java/.../core/domain/entity/Jogador.java)
  - Representa um jogador (nome, posição no tabuleiro, saldo, propriedades possuídas, situação — p.ex. preso).

- `Tabuleiro` (backend/src/main/java/.../core/domain/entity/Tabuleiro.java)
  - Modelo do tabuleiro do jogo, com casas indexadas.

- `Casa` (e subtipos em `core/domain/entity/casa/`)
  - Superclasse para diferentes tipos de casas no tabuleiro.
  - `Propriedade` — casa comprável, com preço, aluguel e propriedade atual.
  - `Prisao` — casa que representa a prisão, com regras de entrada/saída.

- `iniciarUmJogo` (use case) — caso de uso localizado em `core/application/usecase/iniciarUmJogo.java` que mostra como inicializar uma partida.

Repositórios / Ports

- `JogoRepository` (port) — interface que define operações de persistência e consulta para o agregado Jogo.
- `InMemoryJogoRepository` (adapter) — implementação simples utilizada para testes ou para executar o jogo sem um banco de dados externo.

Esses componentes seguem o padrão Port & Adapter: o domínio depende apenas de interfaces (ports) e as implementações concretas ficam na camada de infra.

Ambiente de desenvolvimento

Pré‑requisitos recomendados
- Java JDK 21 (recomenda-se JDK 21 porque o projeto define target/source Java 21 no Maven; ver `backend/pom.xml`).
- Maven (ou usar o wrapper incluido `mvnw` / `mvnw.cmd` no Windows).
- Node.js (versão LTS >= 18) e npm ou yarn — o frontend é gerado com Vite.
- Docker e Docker Compose (para rodar backend + banco facilmente).

Executando o backend localmente (sem Docker)
No Windows (cmd.exe):

```cmd
cd backend
mvnw.cmd clean test spring-boot:run
```

- O projeto inclui o Maven Wrapper (`mvnw`, `mvnw.cmd`) — use-o para garantir consistência de Maven.
- Os testes são executados pelo `mvnw.cmd test`; a fase `verify` também irá gerar e checar cobertura via JaCoCo (configurado no `pom.xml` com limite mínimo de 80%).

Executando o frontend localmente
No Windows (cmd.exe):

```cmd
cd frontend
npm install
npm run dev
```

- O `package.json` (frontend) contém os scripts `dev`, `build`, `preview`.
- A aplicação Vite estará disponível por padrão em `http://localhost:5173` (ver saída do vite).

Docker e Docker Compose (ambiente de desenvolvimento)

O backend inclui um `docker-compose.yml` (na pasta `backend`) que define dois serviços:
- `app`: imagem construída a partir do `backend/Dockerfile`, expõe a porta 8080.
- `db`: container Postgres (versão 15) com credenciais e volume persistente.

As variáveis de ambiente definidas no compose são:
- SPRING_DATASOURCE_URL = jdbc:postgresql://db:5432/app_db
- SPRING_DATASOURCE_USERNAME = postgres
- SPRING_DATASOURCE_PASSWORD = postgres
- SPRING_JPA_HIBERNATE_DDL_AUTO = update

Para subir o backend com Postgres via Docker Compose (no Windows cmd.exe):

```cmd
cd backend
docker-compose up --build
```

- A API ficará disponível em `http://localhost:8080`.
- O Postgres será exposto na porta `5432` (mapeada localmente) com banco `app_db` e usuário `postgres`.

Notas e dicas
- Se preferir rodar o backend localmente com o banco remoto (não via Docker), atualize as propriedades do `application.properties` / `application.yml` ou defina variáveis de ambiente equivalentes.
- O `backend/pom.xml` tem configuração do JaCoCo para gerar relatório de cobertura (ver diretório `backend/htmlReport/`): a pipeline de CI deve publicar esse relatório ou falhar se a cobertura ficar abaixo do mínimo configurado (80%).

Testes e cobertura

- Para rodar testes e gerar relatórios localmente:

```cmd
cd backend
mvnw.cmd clean verify
```

- Os relatórios JaCoCo estarão disponíveis em `backend/target/site` ou em `backend/htmlReport/` dependendo da execução local.

Testes e sua importância

A qualidade do software depende diretamente de uma boa estratégia de testes. Abaixo segue um resumo das camadas de testes recomendadas e por que cada uma é importante para este projeto:

- Testes de Unidade (unit tests):
  - Cobrem regras isoladas do domínio (por exemplo comportamentos de `Jogo`, `Jogador`, `Propriedade`).
  - Devem ser rápidos e independentes de infra (usar `InMemoryJogoRepository` ou mocks).
  - Comando (backend):

```cmd
cd backend
mvnw.cmd -Dtest=*Test test
```

- Testes de Integração (integration tests):
  - Verificam integração entre camadas (ex.: controllers, services e repositórios reais ou em container).
  - Úteis para validar mapeamentos JPA, configuração do Spring e interações com o banco.
  - Rodar com profile de integração ou um banco em container (Docker).

- Testes de Aceitação / End‑to‑End (e2e):
  - Testam fluxos completos, por exemplo: iniciar um jogo via API, executar alguns turnos e validar estado final.
  - Podem ser escritos como testes automatizados no backend ou como testes de integração UI (Playwright / Cypress) no frontend.

- Cobertura (JaCoCo):
  - O projeto está configurado para exigir cobertura mínima (80%). Cobertura alta não garante ausência de bugs, mas reduz regressões.

- Boas práticas adicionais:
  - Escrever testes determinísticos (sem ordem dependente ou temporizações frágeis).
  - Usar fixtures e fábricas de objetos para criar estados de jogo reutilizáveis.
  - Executar testes em pipeline CI para impedir regressões antes do merge.

CI local rápido (smoke test)

```cmd
cd backend
mvnw.cmd -DskipTests=false -DskipITs=true clean verify
```


CI/CD e GitHub Actions (exemplo de pipeline)

Sugestão de etapas para um workflow de CI/CD no GitHub Actions:
1. checkout
2. Setup JDK (Java 21) and Node.js
3. Build & test backend (mvn -B -DskipTests=false clean verify) — garante que JaCoCo roda e coverage é verificada
4. Build & test frontend (npm ci && npm run build)
5. Executar linters e checks (eslint, etc.)
6. Build da imagem Docker do backend e push para registry (opcional)
7. Deploy em ambiente target (staging/production) — por exemplo usando uma action para Kubernetes, Docker Hub, ECS ou outra infra

Notas práticas para GitHub Actions
- Use caching para Maven (`actions/cache`) e para dependências Node (`~/.npm` ou `~/.cache/yarn`) para acelerar builds.
- Separe jobs: `build_and_test_backend`, `build_and_test_frontend`, `publish_images` — isso facilita execução paralela e isolamento de falhas.
- Publish cobertura: extraia os artefatos JaCoCo e publique em relatório de cobertura (ou envie para SonarQube / Codecov se usar essas ferramentas).

Exemplo de checks automáticos que recomendamos no pipeline
- `mvn -B -DskipTests=false clean verify` (falha se cobertura < 80%)
- `npm ci && npm run build` (verifica se frontend compila)
- `npm run lint` (caso esteja configurado)

Observações finais / Sugestões de evolução
- Implementar um adaptador JPA/Repository para persistir `Jogo` em Postgres (agora existe um `InMemoryJogoRepository`).
- Adicionar workflows do GitHub Actions em `.github/workflows/` com jobs separados por serviço.
- Melhorar documentação de API (Swagger/OpenAPI) e adicionar endpoints de exemplo para facilitar integração do frontend.

Git Flow do projeto

Este repositório adota um Git Flow simples com as seguintes regras:

- Branches principais:
  - `main` — contém código pronto para produção; somente merges que passaram por revisão e pipeline devem chegar aqui.
  - `develop` — integra as features concluídas; é a base para deploys de integração/staging.

- Fluxo de trabalho para novos épicos/features:
  1. Crie uma branch a partir de `main` para o épico: `feat/[CODIGO_EPICO]-descricao-curta`.
  2. Trabalhe na branch da feature até concluir o épico. Faça commits pequenos e atômicos.
  3. Abra um Pull Request contra `develop` quando a feature estiver pronta.
  4. O PR deve passar pelos pipelines de CI (build, testes, cobertura) e revisão de código.
  5. Após aprovação e testes em `develop`, o conjunto de features testadas é mesclado em `main` (release) — preferencialmente via Pull Request e com um novo build/CI final.

Exemplo de comandos para criar e enviar uma feature:

```bash
# criar a branch a partir da main
git checkout main
git pull origin main
git checkout -b feat/CODIGO123

# trabalhar, commitar, push
git add .
git commit -m "[RF-X] feat: adiciona lógica X"
git push
```

Regras operacionais importantes
- Nunca fazer merge direto na `main` sem passagem por `develop` e CI (exceto em emergência documentada).
- Os Pull Requests devem conter descrição do que foi feito, status dos testes, e screenshots quando necessário.
- Tags de versão e releases são criadas a partir da `main`.

---

