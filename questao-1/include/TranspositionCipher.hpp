#ifndef TRANSPOSITION_CIPHER_HPP
#define TRANSPOSITION_CIPHER_HPP

#include "Cipher.hpp"

using namespace std;

class TranspositionCipher : public Cipher{
	private:
		string key;
		static const int KEY_SIZE;
		static const int BLOCK_SIZE;
		bool isValidKey(string key);
		string cipherBlock(const string& block, int rows) const;
		string decodeBlock(const string& block, int rows) const;
		string blockOperation(const string& text, Cipher::operation op) const;
	public:
		TranspositionCipher(string key);
		string cipherText(const string& text) const;
		string decodeText(const string& cripto) const;
};



#endif
