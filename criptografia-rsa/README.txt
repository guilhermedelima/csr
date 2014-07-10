Guilherme de Lima Bernardes - 09/0115350
Trabalho de Criptografia

Artigo 13
How to Exchange Secrets with Oblivious Transfer

Implementação de um protocolo de "transferência alheia" para troca de chaves.


Este protocolo é composto por duas partes que desejam trocar mensagens secretas, Bob e Alice. A comunicação entre estas entidades ocorre com chamadas síncronas realizadas através da utilização de sockets do tipo TCP/IP. A classe ObliviousServer representa Bob e possui o papel de servidor. Ela deve ser executada inicialmente para aguardar que Alice (ObliviousClient) inicie o protocolo EOS.
Cada uma das entidades utiliza a classe RSA para gerar um par de chaves pública e privada. O tamanho das chaves geradas pode ser alterado através da variável KEY_SIZE, presente nas classes ObliviousServer e ObliviousClient.
As mensagens secretas SA e SB são definidas em sua respectivas classes, ObliviousClient (Alice) e ObliviousServer(Bob).
Ao fim da execução dos processos será exibido os possíveis valores da chave da outra entidade, com chance de 75% de sucesso.


Classes
	- ObliviousClient.java - Representa uma das partes (Alice) envolvidas no protocolo EOS.
	- ObliviousServer.java - Representa uma das partes (Bob) envolvidas no protocolo EOS.
	- RandomNumbers - Permite a criação de números pseudo aleatórios e também de números primos de N bytes.
	- MathUtils - Disponibiliza métodos para o cálculo de gcd, modPow, euclides estendido e resto chinês para a classe BigInteger.
	- RSA - Classe que permite utilização do protocolo RSA para uma entidade (Possui par de chaves pública e privada).


Compilação
	$ javac -classpath src/ -d bin/ src/br/unb/cripto/oblivious/*


Execução
	1 Passo: Executar servidor ObliviousServer (Bob)
	$ java -classpath bin/ br.unb.cripto.oblivious.ObliviousServer

	2 Passo: Executar cliente ObliviousClient (Alice)
	$ java -classpath bin/ br.unb.cripto.oblivious.ObliviousClient
	
