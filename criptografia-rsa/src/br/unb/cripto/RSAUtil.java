package br.unb.cripto;

import java.math.BigInteger;

public class RSAUtil {

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
	
	//TODO
	static public BigInteger gerarRandomico(){
		BigInteger num = new BigInteger("0");				
		return num;
	}
	
	private static String bytesToString(byte[] byteStream) { 
		String test = ""; 
		for (byte b : byteStream) { 
		    test += Byte.toString(b); 
		} 
		return test; 
	} 
}
