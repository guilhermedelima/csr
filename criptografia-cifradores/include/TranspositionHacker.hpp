#ifndef TRANSPOSITION_HACKER_HPP
#define TRANSPOSITION_HACKER_HPP

#include "Hacker.hpp"

using namespace std;


class TranspositionHacker : public Hacker{
	private:
		const vector<string> dictionary;
	public:
		TranspositionHacker(const vector<string>& dic);
		string breakCipher(const string& cipherText) const;

};


#endif
