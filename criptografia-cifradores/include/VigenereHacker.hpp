#ifndef VIGENERE_HACKER_HPPS
#define VIGENERE_HACKER_HPPS

#include "Hacker.hpp"
#include <array>

using namespace std;


class VigenereHacker : public Hacker{
	public:
		static const array<double, 26> frequency_dictionary;
		VigenereHacker();
		string breakCipher(const string& cipherText) const;
};



#endif
