# FOCUS
## Sobre o projeto
### Integrantes
* Ana Cristina;
* Arthur Rodrigues;
* Rodolfo Lopes.

### Professores envolvidos
* Max do Val Machado;
* Sandro Jerônimo de Almeida;
* Wladmir Cardoso Brandão.

### Proposta
A proposta desse projeto é que ele seja um auxiliador de ensino de pessoas com algum grau de deficiência
intelectual, com um foco em, principalmente, crianças. O protótipo possui o início de um sistema de gameficação
e também possui um sistema básico de diagnóstico de dificuldades nas áreas básicas da matemática (adição, subtração, multiplicação e divisão).

## Sobre as implementações

### Dentre os CRUD's disponíveis para teste, estão:
* CRUD de mensagens completo;
* CRUD de atividades de múltipla escolha completo;
* CRUD de conteúdos (matérias) completo;
* CRUD de usuários, **nele, somente é possível atualizar os dados e criar contas de alunos (nesse caso, apenas para testes, pois a proposta do site não inclui o usuário poder criar e deletar contas)**.

### Implementações de Sistemas Inteligentes:
* O site possui sistema de resolução infinita de questões desde que haja ao menos duas atividades disponíveis (os comandos SQL
disponibilizam 15 questões para o aluno de login: login_aluno1 | senha: 123123123);
* O site possui diagnóstico de áreas de dificuldade do aluno conforme ele resolve exercícios, mostrando uma tabela contendo os números de erros e acertos
de questões das quatro operações básicas;
* O sistema de resolução infinita de questões tenta sempre adaptar o nível de dificuldade das questões futuras se baseando nos dados da questão anterior
e o resultado do aluno. Caso o aluno acerte a questão, a preferência será de que a próxima questão seja de um tema mais difícil ou dificuldade maior; caso erre, será uma questão do mesmo tema, porém mais fácil.

## Para teste das implementações, é necessário
* instalação do postgreSQL e phppgadmin;
* criação manual da conta padronizada do phppgadmin. Login: ti2cc  ----  Senha: ti@cc;
* criação manual da base de dados com nome **"Focus"** e, dentro dela, no esquema público, fazer a inserção dos códigos SQL disponíveis no repositório;
* para rodar o programa, execute o arquivo **"App.java"** e entre no localhost:4567;
* Ao fazer o clone do projeto para a máquina pessoal, em alguns casos, a pasta *"src/test/java"* as vezes não é clonada e isso gera erros.
Se for esse o caso, dentro do diretório *"focus-art/"* apenas é necessário criar essa sequência de pastas, atualizar e tentar rodar o programa que deverá funcionar normalmente.

**Pode haver a necessidade de instalação de outras dependências a depender da máquina do usuário**
