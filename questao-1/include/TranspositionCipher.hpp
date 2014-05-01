#ifndef TRANSPOSITION_HPP
#define TRANSPOSITION_HPP

#include "Cipher.hpp"

using namespace std;

class TranspositionCipher : public Cipher{
	private:
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
