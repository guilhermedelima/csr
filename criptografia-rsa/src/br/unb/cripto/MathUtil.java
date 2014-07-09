package br.unb.cripto;

import java.math.BigInteger;

public class MathUtil {

	/*
	 * Algoritmo para cálculo do maior divisor comum para BigInteger
	 */
	public static BigInteger gcdEuclides(BigInteger a, BigInteger b){

		if( b.equals(BigInteger.ZERO) ){
			return a;
		}

		return gcdEuclides(b, a.mod(b));
	}

	/*
	 * Algoritmo para multiplicação + mod para BigInteger 
	 */
	public static BigInteger modPow(BigInteger base, BigInteger e, BigInteger m){
		BigInteger result; 
		
		result = BigInteger.ONE;
		base = base.mod(m);
		
		for (int i = 0; i < e.bitLength(); ++i) {
			if (e.testBit(i)) {
				result = result.multiply(base).mod(m);
			}
			base = base.multiply(base).mod(m);
		}
		return result;
	}

	/*
	 * Algoritmo de Euclides Estendido para cálculo de a*p + b*q = gcd
	 */
	public static BigInteger[] gcdExtended(BigInteger p, BigInteger q) {
		if (q.equals(BigInteger.ZERO))
			return new BigInteger[] { p, BigInteger.ONE, BigInteger.ZERO };

		BigInteger[] vector = gcdExtended(q, p.mod(q) );
		BigInteger d = vector[0];
		BigInteger a = vector[2];
		BigInteger b = vector[1].subtract(p.divide(q).multiply(vector[2]));

		return new BigInteger[] { d, a, b };
	}

	/*
	 * Algoritmo do Resto Chinês
	 * Retorna quatro raízes da equação x² = c mod p*q (2 < q <= p)
	 */
	public static BigInteger[] quadraticCongruence(BigInteger c, BigInteger p, BigInteger q){

		BigInteger r, s, m, m1, t, t1, n, sum1, sum2;
		BigInteger[] modInverse; 

		n = p.multiply(q);

		r = c.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
		s = c.modPow(q.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), q);

		modInverse = gcdExtended(p, q);

		sum1 = modInverse[1].multiply(p).multiply(s);
		sum2 = modInverse[2].multiply(q).multiply(r);

		m = sum1.add(sum2).mod(n);
		m1 = m.multiply(BigInteger.valueOf(-1)).mod(n);

		t = sum1.subtract(sum2).mod(n);
		t1 = t.multiply(BigInteger.valueOf(-1)).mod(n);

		return new BigInteger[] {t, t1, m, m1};
	}
}
