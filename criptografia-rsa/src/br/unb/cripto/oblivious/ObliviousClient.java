package br.unb.cripto.oblivious;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

import br.unb.cripto.MathUtil;
import br.unb.cripto.RSA;
import br.unb.cripto.RandomNumbers;

/*
 * Classe Que representa Alice no protocolo Oblivious Transfer
 */
public class ObliviousClient {

	public static final String SERVER_HOST = "127.0.0.1";
	public static final int KEY_SIZE = 1;
	public static final BigInteger THREE = BigInteger.valueOf(3);
	public static final BigInteger FOUR = BigInteger.valueOf(4);

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Socket socketClient;
		ObjectOutputStream oos;
		ObjectInputStream ois;

		socketClient = new Socket(SERVER_HOST, 9090);

		oos = new ObjectOutputStream(socketClient.getOutputStream());
		ois = new ObjectInputStream(socketClient.getInputStream());

		try{
			BigInteger SA, SB;
			
			System.out.println("--- ALICE ---\n");
			
			//Definindo Mensagem secreta de Alice
			SA = new BigInteger("100");
			
			//Processando Mensagem secreta de Bob
			SB = processClient(SA, oos, ois);
			
			System.out.println("Possible Secret Bob = " + SB);
			
		}finally{
			oos.close();
			ois.close();
			socketClient.close();
		}
	}
	
	/*
	 * Processa o protocolo EOS e retorna a mensagem secreta de Bob com 75% de chances de Sucesso.
	 */
	public static BigInteger processClient(BigInteger SA, ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException{
		
		RandomNumbers random;
		RSA bobRSA, aliceRSA;
		BigInteger x, cx, cy, bobRoot, aliceRoot;
		BigInteger[] aliceRoots;
		BigInteger bobPossiblePrime;
		BigInteger Ba, Bb;
		int gammaA;
		BigInteger diff;
		BigInteger SB;
		BigInteger criptoBob;

		random = new RandomNumbers();

		/*
		 * 1 Passo
		 * Definição da chave de Alice (SA tem que ser menor que Na)
		 */
		System.out.println("1 Step");
		System.out.println("Computing Alice RSA Keys");
		aliceRSA = new RSA(random, KEY_SIZE);
		
		//RSA vazio para Bob
		bobRSA = new RSA();
		 
		System.out.println("Sending Na to Bob");
		System.out.println("Sending e(alice) to Bob");
		oos.writeObject( aliceRSA.getN() );
		oos.writeObject( aliceRSA.getPublicKey() );
		
		System.out.println("Receiving Nb from Bob");
		System.out.println("Receiving e(bob) from Bob\n");
		bobRSA.setN( (BigInteger) ois.readObject() );
		bobRSA.setPublicKey( (BigInteger) ois.readObject() );
		
		/*
		 * 2 Passo
		 * Alice escolhe X <= Nb e calcula cx = x² mod Nb
		 */
		x = random.getBlumBlumShubNumber(KEY_SIZE).mod(bobRSA.getN());
		cx = x.multiply(x).mod(bobRSA.getN());
		
		System.out.println("2 Step");
		System.out.println("Computing X and calculating Cx");
		System.out.println("Sending Cx to Bob\n");
		oos.writeObject( cx );
		
		/*
		 * 4 Passo
		 * Alice recebe raiz de bob e veririca se gcd(x-raiz, Nb) = pb ou qb
		 * Se for p ou q gammaA=0 , se não gammaA=1 
		 */
		System.out.println("4 Step");
		System.out.println("Receiving root x' from Bob");
		bobRoot = (BigInteger) ois.readObject();
		
		diff = x.subtract(bobRoot).mod(bobRSA.getN());
		bobPossiblePrime = MathUtil.gcdEuclides(bobRSA.getN(), diff);
		
		if( !bobRSA.getN().equals(bobPossiblePrime) &&
				bobRSA.getN().mod(bobPossiblePrime).equals(BigInteger.ZERO) && 
				!bobPossiblePrime.equals(BigInteger.ONE)){
			
			System.out.println("Alice discovered Nb factorization");
			gammaA = 0;
			
			BigInteger secondPrime = bobRSA.getN().divide(bobPossiblePrime);
			bobRSA.setPhiN(bobPossiblePrime.subtract(BigInteger.ONE).multiply(secondPrime.subtract(BigInteger.ONE)));
			bobRSA.setPrivateKey( bobRSA.getPublicKey().modInverse(bobRSA.getPhiN()) );
			
		}else{
			System.out.println("Alice cant fator");
			gammaA = 1;
			bobRSA.setPrivateKey( null );
		}
		
		System.out.println("gamma Alice = " + gammaA + "\n");
		
		/*
		 * 6 Passo
		 * Alice resolve y² = cy mod Na e escolhe uma raiz
		 */
		System.out.println("6 Step");
		System.out.println("Receiving Cy from Bob");
		cy = (BigInteger) ois.readObject();
		
		aliceRoots = MathUtil.quadraticCongruence(cy, aliceRSA.getP(), aliceRSA.getQ());
		aliceRoot = aliceRoots[ Integer.valueOf(random.getBlumBlumShubNumber(1).mod(FOUR).toString()) ];
		//TODO Remover Linha
//		aliceRoot = aliceRoots[1];
		
		System.out.println("Sending random root y' to Bob\n");
		oos.writeObject(aliceRoot);
		
		/*
		 * 8 Passo
		 * Alice e Bob trocam B (garantia para caso de não fatoração)
		 */
		System.out.println("8 Step");
		System.out.println("Receiving Bb from Bob");
		Bb = (BigInteger) ois.readObject();
		
		StringBuilder xorString = new StringBuilder();
		xorString.append('0');
		for(int i=0; i<SA.bitLength(); i++){
			xorString.append(gammaA);
		}
		
		Ba = SA.xor( new BigInteger(xorString.toString(), 2) );
		oos.writeObject(Ba);
		
		System.out.println(Bb + "\n");
		
		/*
		 * 9 Passo
		 * Enviar para bob SA criptagrafado
		 * Receber de bob SB criptografado
		 */
		System.out.println("9 Step");
		System.out.println("Receiving E(SB) from Bob\n");
		criptoBob = (BigInteger) ois.readObject();
		
		oos.writeObject( aliceRSA.cipherText(SA) );
		
		SB = bobRSA.getPrivateKey() == null ? Bb : bobRSA.decodeText(criptoBob);
		
		return SB;
	}



}
