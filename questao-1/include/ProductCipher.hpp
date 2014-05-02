#ifndef PRODUCT_CIPHER_HPP
#define PRODUCT_CIPHER_HPP

#include "Cipher.hpp"

using namespace std;


class ProductCipher : public Cipher{
	private:
		Cipher *transpositionCipher;
		Cipher *vigenereCipher;
	public:
		ProductCipher(string transpositionKey, string vigenereKey);
		~ProductCipher();
		string cipherText(const string& text) const;
		string decodeText(const string& cripto) const;
};




#endif
