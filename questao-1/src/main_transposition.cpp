#include <iostream>
#include <stdlib.h>
#include <fstream>
#include <string>
#include <vector>
#include "Cipher.hpp"
#include "Hacker.hpp"

using namespace std;

int main(int argc, char *argv[]){

	if( argc != 4 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <KEY_FILE> <DICTIONARY>" << endl;
		exit(-1);
	}

	ifstream ikey;
	ifstream itext;
	ifstream idic;

	string key;
	string text;
	string cripto;
	string word;
	vector<string> dic;

	Cipher *cipher;
	Hacker *hacker;

	idic.open(argv[3]);
	ikey.open(argv[2]);
	itext.open(argv[1]);

	getline(ikey, key);
	getline(itext, text);

	while( getline(idic, word) ){
		dic.push_back(word);
	}

	idic.close();
	ikey.close();
	itext.close();

	try{	
		cipher = new TranspositionCipher(key);
		hacker = new TranspositionHacker(dic);

		cripto = cipher->cipherText(text);

		cout << "TEST TRANSPOSITION CIPHER" << endl;
		cout << "CRIPTO: " << cripto << endl;
		cout << "P.TEXT: " << cipher->decodeText(cripto) << endl;

		cout << endl << "TEST TRANSPOSITION HACKER" << endl;
		cout << hacker->breakCipher(cripto) << endl;

		delete cipher;
		delete hacker;

	}catch(exception& e){
		cout << e.what() << endl;
		exit(-1);
	}

	return 0;
}
