package br.unb.cripto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;


public class Main {

	private static final String MESSAGE_FILE = "resources/Message.txt";
	private static final Integer KEY_SIZE = 64;
	
	public static void main(String[] args) {
		
		BigInteger cripto;
		RandomNumbers numberGenerator;
		RSA service;
		String message;
		
		message = readMessage();
		numberGenerator = new RandomNumbers();
		
		System.out.println("Calculating prime numbers of RSA..." + '\n');
		
		service = new RSA(numberGenerator, KEY_SIZE);
		cripto = service.cipherText(message);
		
		System.out.println("Plain Text");
		System.out.println(message + "\n");
		
		System.out.println("Cripto RSA");
		System.out.println(cripto + "\n");
		
		System.out.println("Decode Text");
		System.out.println(service.decodeText(cripto));
	}
	
	
	private static String readMessage(){
		
		InputStream is;
		BufferedReader reader;
		String line;
		StringBuilder builder;
		
		try{
			is = new FileInputStream(MESSAGE_FILE);
			reader = new BufferedReader(new InputStreamReader(is));
			builder = new StringBuilder();
			
			while( (line=reader.readLine()) != null ){
				builder.append(line);
			}
			
			reader.close();
			is.close();
		
		}catch(Exception e){
			builder = null;
			e.printStackTrace();
			System.exit(-1);
		}
		
		return builder.toString();
	}
	
}
