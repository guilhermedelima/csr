package br.unb.cripto;

import java.math.BigInteger;

public class RSA {

	private BigInteger p, q, n, phiN;
    private BigInteger publicKey;
    private BigInteger privateKey; 
    
    public RSA(RandomNumbers numberGenerator, int keySize) {
    	
    	BigInteger e, d;
    	
    	p = numberGenerator.getLargePrime(keySize);
    	q = numberGenerator.getLargePrime(keySize);
    	
    	n = p.multiply(q);
    	phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    	
    	do{
    		e = numberGenerator.getBlumBlumShubNumber(keySize);
    		
    	}while( e.compareTo(phiN) >= 0 || !MathUtil.gcdEuclides(e, phiN).equals(BigInteger.ONE) );
    	
    	d = e.modInverse(phiN);
    	
    	this.publicKey = e;
    	this.privateKey = d;
    	
//    	System.out.println("RSA");
//    	System.out.println("p: " +p);
//    	System.out.println("q: " +q);
//    	System.out.println("n: " +n);
//    	System.out.println("phiN: " +phiN);
//    	System.out.println("e: " +e);
//    	System.out.println("d: " +d);
	}
	
	public BigInteger cipherText(String plainText){
		BigInteger messageAsNumber;
		
		messageAsNumber = new BigInteger(plainText.getBytes());
		
		return messageAsNumber.modPow(publicKey, n);
	}
	
	public String decodeText(BigInteger criptoMessage){
		String message;
		
		message = new String(criptoMessage.modPow(privateKey, n).toByteArray());
		
		return message;
	}
	
}