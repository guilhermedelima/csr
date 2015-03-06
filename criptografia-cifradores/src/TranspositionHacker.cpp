#include "TranspositionHacker.hpp"
#include "Cipher.hpp"
#include <algorithm>
#include <iostream>

using namespace std;

TranspositionHacker::TranspositionHacker(const vector<string>& dic) : dictionary(dic){
}


string TranspositionHacker::breakCipher(const string& cipherText) const{

	int i;
	bool wasFound;
	Cipher *cipher;
	string key, plainText;

	key = "12345678";

	do{
		cipher = new TranspositionCipher(key);
		
		plainText = cipher->decodeText(cipherText);

		for(i=0; i<(int)dictionary.size() && plainText.find(dictionary[i])!=string::npos; i++);
		
		wasFound = i==(int)dictionary.size();

		delete cipher;

	}while( next_permutation(key.begin(), key.end()) && !wasFound );

	cout << "Last tried key: " << key << endl;

	return wasFound ? plainText : TranspositionHacker::MESSAGE_ERROR;
}


