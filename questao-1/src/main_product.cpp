#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>
#include <exception>
#include "Cipher.hpp"

using namespace std;

int main(int argc, char *argv[]){

	if( argc != 4 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <VIGENERE_KEY> <TRANSPOSITION_KEY>";
		cout << endl;
		exit(-1);
	}

	Cipher *cipher;
	fstream iVigenereKey, iTranspositionKey;
	fstream iText;

	string text, vigenereKey, transpositionKey, cripto;
	string word;
	
	iText.open(argv[1]);
	iVigenereKey.open(argv[2]);
	iTranspositionKey.open(argv[3]);

	getline(iText, text);
	getline(iVigenereKey, vigenereKey);
	getline(iTranspositionKey, transpositionKey);

	iText.close();
	iVigenereKey.close();
	iTranspositionKey.close();

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


