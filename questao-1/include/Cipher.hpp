#ifndef CIPHER_HPP
#define CIPHER_HPP

#include <string>
#include <exception>

using namespace std;

class KeyException : exception{
	private:
		static const char *message;
	public:
		const char *what() const throw();
};

class Cipher{
	protected:
		string key;
		enum operation{ CODE, DECODE };
	public:
		Cipher(string key);
		virtual string cipherText(const string& text) const = 0;
		virtual string decodeText(const string& cripto) const = 0;
};

#include "VigenereCipher.hpp"
#include "TranspositionCipher.hpp"

#endif
