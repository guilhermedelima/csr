package br.unb.cripto;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		 
		RSA cifrador = new RSA();
		byte[] textoCifrado = cifrador.cifrar("mensagem".getBytes()); //mensagem cifrada
		byte[] textoClaro = cifrador.decifrar(textoCifrado); //mensagem decifrada
	}
	
}
