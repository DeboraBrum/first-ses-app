# AWS SES
O SES é um serviço para enviar e-mails. 

### Pré - requisitos

#### Criando um Identity com tipo domínio - Amazon SES
Dentro do serviço Amazon SES, seguimos os seguintes passos: 
- Clicar em Create identity;
- Escolher o tipo (Domain ou Email address)
  - Utilizando o tipo domínio, devo colocar o domínio em uma barra de input;
  - Habilitando o DKIM signatures, ele gera os dados de configuração do domínio.
- Clicar em Create identity;
  - Após isso, é exibido os dados que serão utilizados para configurar o domínio;
  - Podemos fazer o download de mais txt records, para configurar o domínio;
  - Essas informações são cadastradas no sistema que o domínio é gerenciado.
- Após configurar o domínio, a identity será verificada.

#### Criando um Identity com tipo email - Amazon SES
Dentro do serviço Amazon SES, seguimos os seguintes passos: 
- Clicar em Create identity;
- Escolher o tipo (Domain ou Email address);
  - Utilizando o tipo email, devemos informar um email válido;
- Clicar em Create identity;
- Para verificar o email, eles enviam um email para o endereço informado;
- Através desse email, conseguimos verificar a identity;
  - Após isso, podemos enviar um email teste;
- Clicar em Send email test;
- Selecionar o formato;
- Informar o sender;
- Informar o receiver;
- Informar o Subject;
- Informar o conteúdo do email;
- Clicar em send test email.

### Para executar a aplicação
Para essa aplicação, é necessário configurar algumas variáveis de ambiente, com os seguintes comandos: 
```shell
code ~/.bash_profile

export AWS_ACCESS_KEY="SUA_KEY"
export AWS_SECRET_ACCESS_KEY="SEU_SECRET"

source ~/.bash_profile
```

#### rodar o comando
```shell
./build.sh
./start.sh
```