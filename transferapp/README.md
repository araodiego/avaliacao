# Teste Arao Diego Schujmann - 22/06/2023

Projeto: Sistema de agendamento de transferencias financeiras.

Ferramenta utilizada: IntelliJ
Linguagem: Java 11
<b>Frameworks:</b> <br> 
SpringBoot - 2.7.13  - Para ser usado com o JAVA 11 <br>
spring-boot-starter-data-jpa<br>
Objetivo: Facilitar a persistencia utilizando o H2.
<br><br>
Lombok <br>
Objetivo: Para deixar o código mais limpo, eliminar o boiler plate code.
<br><br>
H2 como um banco de dados em memória.
<br><br>
spring-boot-starter-test<br>
Objetivo: Para criar os testes unitários.
<br><br><br><br>
# Arquitetura
<li>Foi criada uma classe <b>Transfers</b> que representa uma transferência entre contas.
<br>
<li>Foi utilizado uma interface FeeCalculator com o objetivo de implementar um Design Pattern chamado Strategy para facilitar 
a implementação das regras para definição de da taxa (Fee).
<br>
<li>Foi criada uma classe <b>TransferService</b> para encapsular o código de negócio do agendamendo da transferência.
<br>
<li> A Interface <b>TransferRepository</b> foi criada para utilizar o poder do spring-boot-data-jpa, de
forma que eu consigo realizar as operações CRUD básicas sem digitar nenhuma linha de código.
<br><br>
Foram criados testes unitários <b>TransferServiceTest</b> para todas as possibilidades de Fee e seus erros.O Framework utilizado foi o JUNIT.


 



    

