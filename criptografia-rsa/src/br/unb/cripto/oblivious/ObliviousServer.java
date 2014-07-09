package br.unb.cripto.oblivious;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import br.unb.cripto.MathUtil;
import br.unb.cripto.RSA;
import br.unb.cripto.RandomNumbers;

public class ObliviousServer {

	public static final int PORT = 9090;
	public static final int KEY_SIZE = 1;
	public static final BigInteger THREE = BigInteger.valueOf(3);
	public static final BigInteger FOUR = BigInteger.valueOf(4);

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		ServerSocket listener;
		ObjectOutputStream oos;
		ObjectInputStream ois;

		listener = new ServerSocket(PORT);

		try {

			Socket socket = listener.accept();
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			try{
				BigInteger SB, SA;
				
				System.out.println("--- BOB ---\n");
				
				//Definindo Mensagem secreta de Bob
				SB = new BigInteger("37");
				
				//Processando Mensagem secreta de Alice
				SA = processServer(SB, oos, ois);
				
				System.out.println("Possible Secret Alice = " + SA);

			}finally {
				oos.close();
				ois.close();
				socket.close();
			}

		}finally {
			listener.close();
		}
	}
	
	/*
	 * Processa o protocolo EOS e retorna a mensagem secreta de Alice com 75% de chances de Sucesso.
	 */
	public static BigInteger processServer(BigInteger SB, ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException{
		
		RandomNumbers random;
		RSA bobRSA, aliceRSA;
		BigInteger y, cx, cy, bobRoot, aliceRoot;
		BigInteger[] bobRoots;
		BigInteger alicePossiblePrime;
		BigInteger Ba, Bb;
		int gammaB;
		BigInteger diff;
		BigInteger SA;
		BigInteger criptoAlice;
		
		random = new RandomNumbers();
		
		/*
		 * 1 Passo
		 * Definição da chave de Bob (SB tem que ser menor que Nb)
		 */
		System.out.println("1 Step");
		System.out.println("Computing Bob RSA Keys");
		bobRSA = new RSA(random, KEY_SIZE);
		
		//RSA vazio para Alice
		aliceRSA = new RSA();
		
		System.out.println("Receiving Na from Alice");
		System.out.println("Receiving e(alice) from Alice");
		aliceRSA.setN( (BigInteger) ois.readObject() );
		aliceRSA.setPublicKey( (BigInteger) ois.readObject() );
		
		System.out.println("Sending Nb to Alice");
		System.out.println("Sending e(bob) to Alice\n");
		oos.writeObject( bobRSA.getN() );
		oos.writeObject( bobRSA.getPublicKey() );
		
		/*
		 * 3 Passo
		 * Bob resolve x² = c mod Nb e escolhe uma raiz
		 */
		System.out.println("3 Step");
		System.out.println("Receiving Cx from Alice");
		cx = (BigInteger) ois.readObject();
		
		bobRoots = MathUtil.quadraticCongruence(cx, bobRSA.getP(), bobRSA.getQ());
		bobRoot = bobRoots[ Integer.valueOf(random.getBlumBlumShubNumber(1).mod(FOUR).toString()) ];
		
		System.out.println("Sending random root x' to Alice\n");
		oos.writeObject(bobRoot);
		
		/*
		 * 5 Passo
		 * Bob escolhe Y <= Na e calcula cy = y² mod Na
		 */
		y = random.getBlumBlumShubNumber(1).mod(aliceRSA.getN());
		cy = y.multiply(y).mod(aliceRSA.getN());
		
		System.out.println("5 Step");
		System.out.println("Sending Cy to Alice\n");
		oos.writeObject( cy );
		
		/*
		 * 7 Passo
		 * Bob recebe raiz de alice e veririca se gcd(y-raiz, Na) = pa ou qa
		 * Se for p ou q gammaB=0 , se não gammaB=1 
		 */
		System.out.println("7 Step");
		System.out.println("Receiving root y' from Alice");
		aliceRoot = (BigInteger) ois.readObject();
		
		diff = y.subtract(aliceRoot).mod(aliceRSA.getN());
		alicePossiblePrime = MathUtil.gcdEuclides(aliceRSA.getN(), diff);
		
		if( !aliceRSA.getN().equals(alicePossiblePrime) && 
				aliceRSA.getN().mod(alicePossiblePrime).equals(BigInteger.ZERO) &&
				!alicePossiblePrime.equals(BigInteger.ONE)){
			
			System.out.println("Bob discovered Na factorization");
			gammaB = 0;
			
			BigInteger secondPrime = aliceRSA.getN().divide(alicePossiblePrime);
			aliceRSA.setPhiN(alicePossiblePrime.subtract(BigInteger.ONE).multiply(secondPrime.subtract(BigInteger.ONE)));
			aliceRSA.setPrivateKey( aliceRSA.getPublicKey().modInverse(aliceRSA.getPhiN()) );
			
		}else{
			System.out.println("Bob cant factor");
			gammaB = 1;
			aliceRSA.setPrivateKey(null);
		}
		
		System.out.println("gamma Bob = " + gammaB + "\n");
		
		/*
		 * 8 Passo
		 * Alice e Bob trocam B (garantia para caso de não fatoração)
		 */
		StringBuilder xorString = new StringBuilder();
		xorString.append('0');
		for(int i=0; i<SB.bitLength(); i++){
			xorString.append(gammaB);
		}
		
		Bb = SB.xor( new BigInteger(xorString.toString(), 2));
		oos.writeObject(Bb);
		
		System.out.println("8 Step");
		System.out.println("Receiving Ba from Alice\n");
		Ba = (BigInteger) ois.readObject();
		
		/*
		 * 9 Passo
		 * Enviar para Alice SB criptagrafado
		 * Receber de Alice SA criptografado
		 */
		oos.writeObject( bobRSA.cipherText(SB) );
		
		System.out.println("9 Step");
		System.out.println("Receiving E(SA) from Alice\n");
		criptoAlice = (BigInteger) ois.readObject();
		
		SA = aliceRSA.getPrivateKey() == null ? Ba : aliceRSA.decodeText(criptoAlice);
		
		return SA;
	}

}
