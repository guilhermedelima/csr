#ifndef HACKER_HPP
#define HACKER_HPP

#include "Cipher.hpp"
#include <vector>
#include <string>

class Hacker{
	public:
		Hacker();
		virtual string breakCipher(const string cipherText, const vector<string>& dictionary) const = 0;
};

#include "TranspositionHacker.hpp"

#endif
