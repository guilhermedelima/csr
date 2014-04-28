#ifndef TRANSPOSITION_HPP
#define TRANSPOSITION_HPP

#include "Cipher.hpp"

using namespace std;

class TranspositionCipher : Cipher{
	private:
		static const int KEY_SIZE;
		static const int BLOCK_SIZE;
		bool isValidKey(string key);
		string cipherBlock(const string& block, int rows) const;
	public:
		TranspositionCipher(string key);
		string cipherText(const string& text) const;
		string decodeText(const string& cripto) const;
};



#endif
