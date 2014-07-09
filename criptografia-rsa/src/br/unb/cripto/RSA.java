package br.unb.cripto;

import java.math.BigInteger;

public class RSA {

	private BigInteger p, q, n, phiN;
    private BigInteger publicKey;
    private BigInteger privateKey; 
    
    public RSA(){
    	
    }
    
    public RSA(RandomNumbers numberGenerator, int keySize) {
    	
    	BigInteger e, d;
    	
//    	p = numberGenerator.getLargePrime(keySize);
//    	q = numberGenerator.getLargePrime(keySize);
    	
    	p = numberGenerator.getPrimeBBS(keySize);
    	q = numberGenerator.getPrimeBBS(keySize);
    	
    	n = p.multiply(q);
    	phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    	
    	do{
    		e = numberGenerator.getBlumBlumShubNumber(keySize);
    		
    	}while( e.compareTo(phiN) >= 0 || !MathUtil.gcdEuclides(e, phiN).equals(BigInteger.ONE) );
    	
    	d = e.modInverse(phiN);
    	
    	this.publicKey = e;
    	this.privateKey = d;
    	
    	System.out.println("RSA");
    	System.out.println("p: " +p);
    	System.out.println("q: " +q);
    	System.out.println("n: " +n);
    	System.out.println("phiN: " +phiN);
    	System.out.println("e: " +e);
    	System.out.println("d: " +d+ "\n");
	}
	
	public BigInteger cipherText(BigInteger messageAsNumber){
		return messageAsNumber.modPow(publicKey, n);
	}
	
	public BigInteger decodeText(BigInteger criptoMessage){
		return criptoMessage.modPow(privateKey, n);
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getPhiN() {
		return phiN;
	}

	public void setPhiN(BigInteger phiN) {
		this.phiN = phiN;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}
	
	
	
}