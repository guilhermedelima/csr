#ifndef HACKER_HPP
#define HACKER_HPP

#include <vector>
#include <string>

using namespace std;

class Hacker{
	public:
		Hacker();
		virtual string breakCipher(const string cipherText, const vector<string>& dictionary) const = 0;
};

#include "TranspositionHacker.hpp"

#endif
