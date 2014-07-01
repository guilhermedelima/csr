package br.unb.cripto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;

public class RSAUtil {

	private static final String LRNG = "/dev/urandom";
//	private static final String LRNG = "/dev/random";
	
	private static final Integer BUFFER_SIZE = 1024;
	private static final Integer PRIME_SIZE = 4;
	private static final Integer SEED_SIZE = 4;

	//MDC de dois números inteiros grandes
	public static BigInteger gcdEuclides(BigInteger a, BigInteger b){

		BigInteger mod = a.mod(b);

		if( mod.equals(BigInteger.ZERO) ){
			return b;
		}

		return gcdEuclides(b, mod);
	}

	//TODO
	static public BigInteger encontrarNumeroPrimoGrande(int numeroDeBits){
		BigInteger num = new BigInteger("0");				
		return num;
	}

	//TODO
	static public BigInteger gerarChaveDeDecifracao(BigInteger chaveDeCifracao, BigInteger p, BigInteger q){
		BigInteger num = new BigInteger("0");				
		return num;
	}

	//TODO
	static public BigInteger gerarChaveDeCifracao(BigInteger p, BigInteger q){
		BigInteger num = new BigInteger("0");				
		return num;
	}
	
	
	
	public static BigInteger getBlumBlumShubNumber(int nBits){
		
		BigInteger n, p, q, seed; 
		BigInteger two, four, three;
		BigInteger x;
		StringBuilder builder;
		
		two = new BigInteger("2");
		three = new BigInteger("3");
		four = new BigInteger("4");
		
		do{
			p = getLinuxRandomNumber(PRIME_SIZE);
		}while(p.mod(two).equals(BigInteger.ZERO) || p.mod(four).compareTo(three) != 0 || !millerRabinTest(p));
		
		do{
			q = getLinuxRandomNumber(PRIME_SIZE);
		}while(q.mod(two).equals(BigInteger.ZERO) || q.mod(four).compareTo(three) != 0 || !millerRabinTest(q));
		
		n = p.multiply(q);
		
		do{
			seed = getLinuxRandomNumber(SEED_SIZE);
		}while( !gcdEuclides(seed, n).equals(BigInteger.ONE) );
		
		builder = new StringBuilder();
		x = seed.multiply(seed).mod(n);
		
		for(int i=0; i<nBits; i++){
			x = x.multiply(x).mod(n); 
			builder.append( x.mod(two) );
		}
		
		return new BigInteger(builder.toString(), 2);
	}
	
	public static boolean millerRabinTest(BigInteger num, int iteracao){
		
		BigInteger ZERO = new BigInteger("0");
		BigInteger UM = new BigInteger("1");
		BigInteger DOIS = new BigInteger("2");
		
		if(num.compareTo(ZERO) == 0 || num.compareTo(UM) == 0)
			return false;
		
		if(num.compareTo(DOIS) == 0)
			return true;
					
		if(num.mod(DOIS).compareTo(ZERO) == 0)
			return false;
		
		BigInteger s = num.subtract(UM);
		while(s.mod(DOIS).compareTo(ZERO) == 0){
			s = s.divide(DOIS);
		}
		
		for(int i = 0; i < iteracao; i++){
			BigInteger r = ;//TODO Numero randomico positivo 
			
            BigInteger a = r.mod(num.subtract(UM)).add(UM); 
            BigInteger temp = s;
            
            BigInteger mod = modPow(a, temp, num);
            
            while (temp.compareTo(num.subtract(UM)) != 0 && mod.compareTo(UM) != 0 && mod.compareTo(num.subtract(UM)) != 0)
            {
                mod = mulMod(mod, mod, num);
                temp = temp.multiply(DOIS);
            }
            if(mod.compareTo(num.subtract(UM)) != 0 && temp.mod(DOIS).compareTo(ZERO) != 0)
                return false;
		}
		
		return true;
	}
	
	private static BigInteger modPow(BigInteger a, BigInteger b, BigInteger c){
		BigInteger res = new BigInteger("1");
		
        for (BigInteger i = new BigInteger("0"); i.compareTo(b) < 0; i.add(new BigInteger("1")))
        {
            res = res.multiply(a);
            res = res.mod(c);
        }
        return res.mod(c);
	}
	
	public static BigInteger mulMod(BigInteger a, BigInteger b, BigInteger mod){
		return a.multiply(b).mod(mod);	
	}
	
	/* Gerador de números randômicos verdadeiros (Entropia -> Usar /dev/random) */
	public static BigInteger getLinuxRandomNumber(int nBytes){

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
