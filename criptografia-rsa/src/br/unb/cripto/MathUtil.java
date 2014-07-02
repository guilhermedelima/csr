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

}
