#ifndef TRANSPOSITION_HPP
#define TRANSPOSITION_HPP

#include "Cipher.hpp"

using namespace std;

class TranspositionCipher : Cipher{
	private:
		void cipherBlock(string& block) const;
	public:
		TranspositionCipher(string key);
		string cipherText(const string& text) const;
};



#endif
