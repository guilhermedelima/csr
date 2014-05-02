#ifndef TRANSPOSITION_HACKER_HPP
#define TRANSPOSITION_HACKER_HPP

#include "Hacker.hpp"

using namespace std;


class TranspositionHacker : public Hacker{
	private:
		static const char *MESSAGE_ERROR;
	public:
		TranspositionHacker();
		string breakCipher(const string cipherText, const vector<string>& dictionary) const;

};


#endif
