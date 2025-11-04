# Cariopoly

---

## Visão Geral

**Cariopoly** é uma implementação didática do jogo estilo *Monopoly*. O repositório está dividido em duas partes principais:

- `backend/` — API em **Spring Boot** (Java + Maven) com lógica de domínio, casos de uso e adaptadores de infra.
- `frontend/` — cliente web em **Vite + React + TypeScript**.

> Este README apresenta: arquitetura, entidades do domínio, instruções de desenvolvimento, Docker, testes e Git Flow adotado.

---

## Sumário

- [Visão geral](#-visão-geral)
- [Arquitetura](#-arquitetura---hexagonal-e-clean-architecture)
- [Entidades e repositórios](#-entidades-principais-exemplos)
- [Ambiente de desenvolvimento](#-ambiente-de-desenvolvimento)
- [Docker / Docker Compose](#-docker--docker-compose)
- [Testes e cobertura](#-testes-e-cobertura)
- [Testes: importância e estratégias](#-testes-e-sua-importância)
- [CI/CD (GitHub Actions)](#-cicd-github-actions)
- [Git Flow do projeto](#-git-flow-do-projeto)

---

## 🏗 Arquitetura — Hexagonal e Clean Architecture

O projeto segue os princípios de **Arquitetura Hexagonal (Ports & Adapters)** e se inspira em **Clean Architecture**:

- **Núcleo (Domain)**: entidades e regras de negócio puras.
- **Use Cases / Application**: orquestram a lógica entre entidades e portas (ex.: `iniciarUmJogo`).
- **Ports**: interfaces que definem contratos (ex.: `JogoRepository`).
- **Adapters / Infra**: implementações concretas dos ports (ex.: `InMemoryJogoRepository`, possível adaptador JPA).

**Benefícios**:
- Separação clara entre regras de negócio e infra‑estrutura.
- Testabilidade e facilidade para trocar implementações de infra.

---

## Entidades principais

- **`Jogo`** — representa uma partida (estado do jogo, jogadores, turno).
- **`Jogador`** — representa um jogador (nome, posição, saldo, propriedades, situação).
- **`Tabuleiro`** — modelo do tabuleiro com casas indexadas.
- **`Casa`** (superclasse) e subtipos em `core/domain/entity/casa/`:
  - **`Propriedade`** — casa comprável com preço e aluguel.
  - **`Prisao`** — regras de prisão e saída.
- **Use case**: `iniciarUmJogo` (localizado em `core/application/usecase/iniciarUmJogo.java`).
- **Ports**: `JogoRepository` — contrato de persistência.
- **Adapters**: `InMemoryJogoRepository` (infra/adapter) — implementação para testes/demonstração.

---

## Ambiente de desenvolvimento

**Pré‑requisitos recomendados**

- Java JDK **21** (o `pom.xml` define target/source Java 21).
- Maven (ou use o wrapper: `mvnw` / `mvnw.cmd`).
- Node.js LTS (>= 18) e npm/yarn.
- Docker & Docker Compose (para executar backend + Postgres).

### Executando o backend localmente (Windows - cmd.exe)

```cmd
cd backend
mvnw.cmd clean test spring-boot:run
```

- Observação: use o `mvnw.cmd` incluído para garantir a versão do Maven.

### Executando o frontend localmente (Windows - cmd.exe)

```cmd
cd frontend
npm install
npm run dev
```

- O Vite tipicamente expõe a aplicação em `http://localhost:5173`.

---

## Docker / Docker Compose

O `backend/docker-compose.yml` define dois serviços:

- `app` — constrói a partir de `backend/Dockerfile` e expõe a porta **8080**.
- `db` — container **Postgres 15** (porta 5432) com volume persistente.

Variáveis de ambiente definidas no compose:

- `SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/app_db`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=postgres`
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update`

### Subir com Docker Compose (Windows - cmd.exe)

```cmd
cd backend
docker-compose up --build
```

- API disponível: `http://localhost:8080`.

---

## Testes e cobertura (JaCoCo)

- Para rodar testes e gerar relatórios locais:

```cmd
cd backend
mvnw.cmd clean verify
```

- O JaCoCo está configurado no `pom.xml` com **verificação mínima de cobertura: 80%**.
- Relatórios podem aparecer em `backend/target/site` ou em `backend/htmlReport/`.

---

## Testes e sua importância

**Por que testar?**
- Reduz regressões.
- Documenta comportamento esperado do sistema.
- Permite refatorações com segurança.

**Tipos de testes recomendados**

1. **Testes de Unidade**
   - Cobrem regras isoladas do domínio (`Jogo`, `Jogador`, `Propriedade`).
   - Devem ser rápidos e independentes de infra.
   - Executar:

```cmd
cd backend
mvnw.cmd -Dtest=*Test test
```

2. **Testes de Integração**
   - Validam integração entre camadas (controllers, services, repositórios reais).
   - Úteis para validar mapeamentos JPA e configuração do Spring.
   - Podem rodar usando um banco em container (Docker).

3. **Testes de Aceitação / E2E**
   - Validam fluxos completos (ex.: iniciar um jogo via API e executar turnos).
   - Implementáveis no backend ou via testes de UI (Playwright / Cypress).

**Boas práticas**
- Escrever testes determinísticos.
- Usar fixtures/factories para estados do jogo.
- Rodar testes no CI para bloquear regressões.

**Smoke test rápido (CI local)**

```cmd
cd backend
mvnw.cmd -DskipTests=false -DskipITs=true clean verify
```

---

## CI/CD (GitHub Actions) — fluxo sugerido

**Jobs sugeridos**

1. `build_backend` — Setup JDK 21, rodar `mvn clean verify` (gera e checa JaCoCo).
2. `build_frontend` — Setup Node.js 18, `npm ci` e `npm run build`.
3. `docker_build_and_push` — opcional: build e push da imagem Docker (requer secrets).

**Dicas**
- Use `actions/cache` para Maven e Node para acelerar builds.
- Separe jobs para executar em paralelo e isolar falhas.
- Publique os relatórios JaCoCo como artifacts ou envie para Codecov/SonarQube.

---

##  Git Flow do projeto

**Branches principais**

- `main` — código pronto para produção (somente merges aprovados e com CI verde).
- `develop` — integra features concluídas; base para staging/integration.

**Fluxo para épicos / features**

1. Crie a branch do épico a partir de `main`:

```bash
# criar a branch a partir da main
git checkout main
git pull origin main
git checkout -b feat/CODIGO123-descricao
```

2. Trabalhe na branch (`feat/[CODIGO_EPICO]-...`), commite frequentemente.
3. Abra um Pull Request para `develop` quando pronto.
4. O PR deve passar pelo pipeline (build, testes, cobertura) e revisão.
5. Após validação em `develop`, o conjunto de features é mergeado em `main` (release).

**Regras operacionais**
- **Não** faça merge direto em `main` sem passar por `develop` e CI (exceto emergência documentada).
- PRs devem conter descrição, checklist de testes e screenshots quando relevantes.
- Tags e releases são criados a partir de `main`.

---