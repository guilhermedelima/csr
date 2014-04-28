#ifndef VIGENERE_HPP
#define VIGENERE_HPP

#include "Cipher.hpp"

using namespace std;

class VigenereCipher : Cipher{
	private:
		static const int DICTIONARY_SIZE;
		char *dictionary;
		enum operation{ CODE, DECODE };
		int getDictionaryPosition(char letter) const;
		string textOperation(const string& text, VigenereCipher::operation op) const;
	public:
		VigenereCipher(string key);
		~VigenereCipher();
		string cipherText(const string& text) const;
		string decodeText(const string& cripto) const;
};



#endif
