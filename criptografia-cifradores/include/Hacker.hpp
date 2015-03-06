#ifndef HACKER_HPP
#define HACKER_HPP

#include <vector>
#include <string>

using namespace std;

class Hacker{
	protected:
		static const char *MESSAGE_ERROR;
	public:
		virtual string breakCipher(const string& cipherText) const = 0;
};

#include "TranspositionHacker.hpp"
#include "VigenereHacker.hpp"

#endif
