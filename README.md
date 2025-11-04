# 🏋️ Academia App

Aplicativo Android de gerenciamento de academia desenvolvido em Kotlin como projeto acadêmico.

## 📱 Funcionalidades Implementadas

✅ **Tela de Login**
- Autenticação de usuários
- Cadastro de novos usuários
- Validação de formulários
- Usuário padrão: `admin@academia.com` / `admin`

✅ **Tela Inicial (Home)**
- Boas-vindas personalizadas
- Navegação por cards para Produtos e Serviços
- Botão de logout

✅ **Gerenciamento de Produtos**
- Listagem com RecyclerView
- CRUD completo (Create, Read, Update, Delete)
- Campos: Nome, Descrição, Categoria, Preço, Estoque
- Dialog personalizado para adicionar/editar
- Confirmação antes de deletar
- Formatação de preço em Real (R$)
- Dados de exemplo pré-cadastrados

✅ **Gerenciamento de Serviços**
- Listagem com RecyclerView
- CRUD completo (Create, Read, Update, Delete)
- Campos: Nome, Descrição, Instrutor, Valor, Duração
- Dialog personalizado para adicionar/editar
- Confirmação antes de deletar
- Formatação de valor em Real (R$)
- Dados de exemplo pré-cadastrados

✅ **Banco de Dados SQLite**
- 3 tabelas: usuarios, produtos, servicos
- Persistência local de dados
- DAOs para cada entidade

✅ **Design Moderno e Elegante**
- Material Design 3
- Paleta de cores azul e laranja
- Cards com elevação
- Floating Action Buttons
- TextInputLayout com validações
- Ícones e feedback visual

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **IDE:** Android Studio Narwhal 3 Feature Drop | 2025.1.3
- **Banco de Dados:** SQLite
- **Padrão de Projeto:** DAO (Data Access Object)
- **UI Components:** Material Design 3
- **Minimum SDK:** API 24 (Android 7.0)

## 📦 Estrutura do Projeto
```
app/
├── data/
│   ├── database/
│   │   ├── DatabaseHelper.kt
│   │   ├── UsuarioDao.kt
│   │   ├── ProdutoDao.kt
│   │   └── ServicoDao.kt
│   └── model/
│       ├── Usuario.kt
│       ├── Produto.kt
│       └── Servico.kt
├── ui/
│   ├── login/
│   │   └── LoginActivity.kt
│   ├── home/
│   │   └── HomeActivity.kt
│   ├── produtos/
│   │   ├── ProdutosActivity.kt
│   │   └── ProdutoAdapter.kt
│   └── servicos/
│       ├── ServicosActivity.kt
│       └── ServicoAdapter.kt
└── res/
    ├── layout/
    ├── values/
    └── ...
```

## 🚀 Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/SEU_USUARIO/academia-app.git
```

2. Abra o projeto no Android Studio Narwhal 3 ou superior

3. Aguarde o Gradle Sync finalizar

4. Execute no emulador ou dispositivo físico:
    - Clique em Run ▶️ ou pressione Shift+F10

5. Faça login com o usuário padrão:
    - Email: `admin@academia.com`
    - Senha: `admin`

## 📸 Capturas de Tela

### Tela de Login
- Login e cadastro de usuários
- Validação de campos

### Tela Home
- Dashboard com cards de navegação
- Boas-vindas personalizadas

### Produtos
- Lista de produtos com RecyclerView
- CRUD completo com dialogs

### Serviços
- Lista de serviços com RecyclerView
- Gerenciamento completo

## 🎨 Paleta de Cores

- **Primary (Azul):** #1E88E5
- **Secondary (Laranja):** #FF6F00
- **Background:** #F5F5F5
- **Success:** #4CAF50
- **Error:** #F44336

## ✅ Requisitos Atendidos

- [x] Tela de Login
- [x] Tela Inicial
- [x] Tela Produto
- [x] Serviço
- [x] SQLite
- [x] CRUD
- [x] RecyclerView + 1 Tela
- [x] Sistema Conectado e Interligado
- [x] Design Moderno e Elegante

## 📝 Dados de Exemplo

### Produtos Pré-cadastrados:
1. Whey Protein - R$ 89,90
2. Creatina - R$ 59,90
3. Luva de Treino - R$ 35,00
4. Garrafa Squeeze - R$ 25,00

### Serviços Pré-cadastrados:
1. Musculação - R$ 150,00
2. Spinning - R$ 80,00
3. Yoga - R$ 70,00
4. CrossFit - R$ 180,00
5. Pilates - R$ 90,00

## 👤 Autor

Projeto acadêmico desenvolvido para demonstrar conhecimentos em:
- Desenvolvimento Android nativo
- Linguagem Kotlin
- Banco de dados SQLite
- Material Design
- Arquitetura de aplicativos móveis

## 📄 Licença

Este projeto é de uso acadêmico.

---

**Desenvolvido com ❤️ e Kotlin**