package br.unb.cripto;


public class Main {

	public static void main(String[] args) {
		 
//		RSA cifrador = new RSA();
//		byte[] textoCifrado = cifrador.cifrar("mensagem".getBytes()); //mensagem cifrada
//		byte[] textoClaro = cifrador.decifrar(textoCifrado); //mensagem decifrada
		
		System.out.println( RSAUtil.getBlumBlumShubNumber(500) );
	}
	
}
