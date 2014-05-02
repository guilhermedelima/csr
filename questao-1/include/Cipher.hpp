#ifndef CIPHER_HPP
#define CIPHER_HPP

#include <string>
#include <exception>

using namespace std;

class KeyException : public exception{
	private:
		static const char *MESSAGE;
	public:
		const char *what() const throw();
};

class Cipher{
	protected:
		enum operation{ CODE, DECODE };
	public:
		virtual string cipherText(const string& text) const = 0;
		virtual string decodeText(const string& cripto) const = 0;
};

#include "VigenereCipher.hpp"
#include "TranspositionCipher.hpp"
#include "ProductCipher.hpp"

#endif
