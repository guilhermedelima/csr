#ifndef VIGENERE_CIPHER_HPP
#define VIGENERE_CIPHER_HPP

#include "Cipher.hpp"

using namespace std;

class VigenereCipher : public Cipher{
	private:
		string key;
		static const int DICTIONARY_SIZE;
		char *dictionary;
		int getDictionaryPosition(char letter) const;
		string textOperation(const string& text, Cipher::operation op) const;
	public:
		VigenereCipher(string key);
		~VigenereCipher();
		string cipherText(const string& text) const;
		string decodeText(const string& cripto) const;
};



#endif
