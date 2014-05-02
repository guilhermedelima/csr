#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>
#include <exception>
#include "Cipher.hpp"

using namespace std;

int main(int argc, char *argv[]){

	if( argc != 5 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <VIGENERE_KEY> <TRANSPOSITION_KEY> <DICTIONARY>";
		cout << endl;
		exit(-1);
	}

	Cipher *cipher;
	fstream iVigenereKey, iTranspositionKey;
	fstream iText, iDic;

	string text, vigenereKey, transpositionKey, cripto;
	string word;
	vector<string> dictionary;
	
	iText.open(argv[1]);
	iVigenereKey.open(argv[2]);
	iTranspositionKey.open(argv[3]);
	iDic.open(argv[4]);

	getline(iText, text);
	getline(iVigenereKey, vigenereKey);
	getline(iTranspositionKey, transpositionKey);

	while( getline(iDic, word) ){
		dictionary.push_back(word);
	}

	iText.close();
	iVigenereKey.close();
	iTranspositionKey.close();
	iDic.close();

	try{
		cipher = new ProductCipher(transpositionKey, vigenereKey);

		cripto = cipher->cipherText(text);

		cout << "TEST PRODUCT CIPHER" << endl;
		cout << "CRIPTO: " << cripto << endl;
		cout << "P.TEXT: " << cipher->decodeText(cripto) << endl;

		delete cipher;

	}catch(exception& e){
		cout << e.what() << endl;
		exit(-1);
	}


	return 0;
}


