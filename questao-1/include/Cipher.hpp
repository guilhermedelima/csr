#ifndef CIPHER_HPP
#define CIPHER_HPP

#include <string>

using namespace std;

class Cipher{
	protected:
		string key;
	public:
		Cipher(string key);
		virtual string cipherText(const string& text) const = 0;
		virtual string decodeText(const string& cripto) const = 0;
};

#include "VigenereCipher.hpp"
#include "TranspositionCipher.hpp"
#include "Util.hpp"

#endif
