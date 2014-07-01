package br.unb.cripto;

import java.math.BigInteger;

public class MathUtil {
	
	public static BigInteger gcdEuclides(BigInteger a, BigInteger b){

		BigInteger mod = a.mod(b);

		if( mod.equals(BigInteger.ZERO) ){
			return b;
		}

		return gcdEuclides(b, mod);
	}
	
	public static BigInteger modPow(BigInteger a, BigInteger b, BigInteger c){
		BigInteger res = BigInteger.ONE;
		
        for (BigInteger i = BigInteger.ZERO; i.compareTo(b) < 0; i.add(BigInteger.ONE))
        {
            res = res.multiply(a);
            res = res.mod(c);
        }
        return res.mod(c);
	}
	
	public static BigInteger mulMod(BigInteger a, BigInteger b, BigInteger mod){
		return a.multiply(b).mod(mod);	
	}

}
