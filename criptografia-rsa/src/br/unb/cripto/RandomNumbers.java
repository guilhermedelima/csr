package br.unb.cripto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;

public class RandomNumbers {

	private static final String LRNG = "/dev/urandom";
	//	private static final String LRNG = "/dev/random";

	private static final Integer BUFFER_SIZE = 1024;
	private static final Integer PRIME_SIZE = 4;
	private static final Integer SEED_SIZE = 4;
	private static final Integer PRIME_PRECISION = 5;
	
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

		p = getPrimeBBS();
		q = getPrimeBBS();

		n = p.multiply(q);

		do{
			seed = getLinuxRandomNumber(SEED_SIZE);
		}while( !MathUtil.gcdEuclides(seed, n).equals(BigInteger.ONE) );

		builder = new StringBuilder();
		x = seed.multiply(seed).mod(n);

		for(int i=0; i<nBytes; i++){
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

		}while( !millerRabinTest(prime) );

		return prime;
	}

	/*
	 * Gerar número primo para método Blum Blum Shub
	 */
	private BigInteger getPrimeBBS(){

		BigInteger prime;

		do{
			prime = getLinuxRandomNumber(PRIME_SIZE);

		}while(prime.mod(FOUR).compareTo(THREE) != 0 || !millerRabinTest(prime));

		return prime;
	}
	
	/*
	 * Teste de Miller-Rabin para verificar se um núemro é primo
	 */	
	private boolean millerRabinTest(BigInteger num){

		if(num.compareTo(BigInteger.ZERO) == 0 || num.compareTo(BigInteger.ONE) == 0)
			return false;
		
		if(num.compareTo(TWO) == 0)
			return true;
					
		if(num.mod(TWO).compareTo(BigInteger.ZERO) == 0)
			return false;
		
		BigInteger s = num.subtract(BigInteger.ONE);
		while(s.mod(TWO).compareTo(BigInteger.ZERO) == 0){
			s = s.divide(TWO);
		}
		
		for(int i = 0; i < PRIME_PRECISION; i++){
			BigInteger r = getLinuxRandomNumber(PRIME_SIZE);
			
			do{
				r = getLinuxRandomNumber(PRIME_SIZE);
			}while( r.compareTo(num) >= 0);
			
            BigInteger a = r.mod(num.subtract(BigInteger.ONE)).add(BigInteger.ONE); 
            BigInteger temp = s;
            
            BigInteger mod = MathUtil.modPow(a, temp, num);
            
            while (temp.compareTo(num.subtract(BigInteger.ONE)) != 0 && mod.compareTo(BigInteger.ONE) != 0 
            		&& mod.compareTo(num.subtract(BigInteger.ONE)) != 0)
            {
                mod = MathUtil.mulMod(mod, mod, num);
                temp = temp.multiply(TWO);
            }
            
            if(mod.compareTo(num.subtract(BigInteger.ONE)) != 0 && temp.mod(TWO).compareTo(BigInteger.ZERO) != 0)
                return false;
		}
		
		return true;
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
