package br.unb.cripto;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

	private BigInteger p; 
    private BigInteger q;
	
    private BigInteger chavePublica;
    private BigInteger chavePrivada;
    
	private int numeroDeBits = 500; 
    
    public RSA() {
    	
    	BigInteger random = RSAUtil.getLinuxRandomNumber(100); //gera um numero aleatório seguro
    	
    	//TODO
    	this.p = new BigInteger("0");
    	this.q = new BigInteger("0");
    	
    	this.gerarChaves(); //Gera as chaves pública e privada
    	
	}
	
	private void gerarChaves(){
		//gera chave publica e privada
		this.chavePublica = RSAUtil.gerarChaveDeCifracao(this.p, this.q);
	}
	
	public byte[] cifrar(byte[] mensagem){
		return this.cifrar(mensagem, chavePublica, new BigInteger("0"));
	}
	
	public byte[] decifrar(byte[] mensagem){
		return this.decifrar(mensagem, this.chavePrivada,  new BigInteger("0"));
	}
	
	//TODO
	private byte[] cifrar(byte[] mensagem, BigInteger chave, BigInteger moduloN){
		return null;
	}
	
	//TODO
	private byte[] decifrar(byte[] mensagem, BigInteger chave, BigInteger moduloN){
		return null;
	}
}