#include <iostream>
#include <stdlib.h>
#include <fstream>
#include <string>
#include "Cipher.hpp"

using namespace std;

int main(int argc, char *argv[]){

	if( argc != 3 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <KEY_FILE>" << endl;
		exit(-1);
	}

	ifstream ikey;
	ifstream itext;

	string key;
	string text;
	string cripto;

	ikey.open(argv[2]);
	itext.open(argv[1]);

	getline(ikey, key);
	getline(itext, text);

	ikey.close();
	itext.close();

	try{	
		TranspositionCipher cipher(key);

		cripto = cipher.cipherText(text);

		cout << "PLAIN TEXT" << endl << text << endl << endl;
		cout << "CRIPTO" << endl << cripto << endl << endl;
		cout << "DECRYPTED TEXT" << endl << cipher.decodeText(cripto) << endl;

	}catch(KeyException& e){
		cout << e.what() << endl;
		exit(-1);
	}

	return 0;
}
