# Gerador de sql consulta base IBGE

## Como utilizar

Basta executar a aplicação Spring, e será feita a conexão automaticamente com o IBGE e gerar um arquivo de `INSERT` e
outro de `UPDATE` numa pasta na raiz do projeto, chamada `sql-files`. Se necessário, altere as propriedades do sql na
classe GenerateSqlService. A pasta `sql-files` foi adicionada no arquivo `.gitignore`.