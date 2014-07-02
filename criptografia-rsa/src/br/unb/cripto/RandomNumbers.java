package br.unb.cripto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;

public class RandomNumbers {

	private static final String LRNG = "/dev/urandom";
	//	private static final String LRNG = "/dev/random";

	private static final Integer BUFFER_SIZE = 1024;
	private static final Integer PRIME_SIZE = 32;
	private static final Integer SEED_SIZE = 64;
	private static final Integer PRIME_PRECISION = 10;
	
	private BigInteger TWO, FOUR, THREE; 
	
	public RandomNumbers(){ 
		TWO = new BigInteger("2");
		THREE = new BigInteger("3");
		FOUR = new BigInteger("4");
	}

	public BigInteger getBlumBlumShubNumber(int nBytes){

		BigInteger n, p, q, seed; 
		BigInteger x;
		StringBuilder builder;
		int nBits;

		nBits = nBytes * Byte.SIZE;
		
		p = getPrimeBBS();
		q = getPrimeBBS();			

		n = p.multiply(q);

		do{
			seed = getLinuxRandomNumber(SEED_SIZE);
		}while( !MathUtil.gcdEuclides(seed, n).equals(BigInteger.ONE) );

		builder = new StringBuilder();
		x = seed.multiply(seed).mod(n);

		for(int i=0; i<nBits; i++){
			x = x.multiply(x).mod(n); 
			builder.append( x.mod(TWO) );
		}

		return new BigInteger(builder.toString(), 2);
	}

	/*
	 * Gerar número Primo de N Bytes
	 */
	public BigInteger getLargePrime(int nBytes){

		BigInteger prime;

		do{
			prime = getLinuxRandomNumber(nBytes);

		}while( !millerRabinTest(prime, nBytes) );

		return prime;
	}

	/*
	 * Gerar número primo para método Blum Blum Shub
	 */
	private BigInteger getPrimeBBS(){

		BigInteger prime;

		do{
			prime = getLinuxRandomNumber(PRIME_SIZE);

		}while(prime.mod(FOUR).compareTo(THREE) != 0 || !millerRabinTest(prime, PRIME_SIZE));

		return prime;
	}
	
	/*
	 * Teste de Miller-Rabin para verificar se um núemro é primo
	 * Realiza a quantidade de testes determinada por PRIME_PRECISION
	 */	
	private boolean millerRabinTest(BigInteger num, int nBytes){
		
		BigInteger a;
		
		if(num.equals(TWO))
			return true;
		
		if( num.equals(BigInteger.ZERO) || num.equals(BigInteger.ONE) || !num.mod(TWO).equals(BigInteger.ONE))
			return false;

		for(int i=0; i<PRIME_PRECISION; i++){
			
			do{
				a = getLinuxRandomNumber(nBytes);
				
			}while( num.subtract(BigInteger.ONE).compareTo(a) <= 0 || a.compareTo(BigInteger.ONE) <= 0);
			
			if(!checkPrimeProperties(num, a))
				return false;
		}
		
		return true;
	}
	
	/*
	 * Teste para verificar as duas propriedades de um núemro primo com a => (1 < a < n-1) 
	 * Escreve número na forma (n-1) = 2^k * q e faz os testes
	 */
	private boolean checkPrimeProperties(BigInteger num, BigInteger a){
		
		BigInteger k, q;
		
		k = BigInteger.ONE;
		q = num.subtract(BigInteger.ONE).divide(TWO);
		
		while( q.mod(TWO).equals(BigInteger.ZERO) ){
			q = q.divide(TWO);
			k = k.add(BigInteger.ONE);
		}
		
		/* Teste a^q mod n = 1 */
		if( a.modPow(q, num).equals(BigInteger.ONE) )
			return true;
		
		/* Testa a^((2^i)*q) mod n = -1 para 0...k-1  */
		for(BigInteger i=BigInteger.ZERO, exp = q; i.compareTo(k)<0; i = i.add(BigInteger.ONE)){
			
			if( a.modPow(exp, num).equals(num.subtract(BigInteger.ONE)) ){
				return true;
			}
					
			exp = exp.multiply(TWO);
		}
		
		return false;
	}

	/* 
	 * Gerador de números randômicos verdadeiros (Entropia -> Usar /dev/random) 
	 */
	private static BigInteger getLinuxRandomNumber(int nBytes){

		InputStream is;
		ByteArrayOutputStream data;
		int read, offset;
		byte buffer[];
		BigInteger bigNum;

		try{
			is = new FileInputStream(LRNG);
			data = new ByteArrayOutputStream();
			buffer = new byte[BUFFER_SIZE];

			while( nBytes > 0 && (read = is.read(buffer, 0, BUFFER_SIZE)) != -1){
				offset = read < nBytes ? read : nBytes;
				nBytes -= offset;

				data.write(buffer, 0, offset);
			}

			bigNum = new BigInteger(1, data.toByteArray());

			data.close();
			is.close();

		}catch(Exception e){
			e.printStackTrace();
			bigNum = null;
		}

		return bigNum;
	}
}
