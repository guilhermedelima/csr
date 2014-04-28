#include <iostream>
#include <fstream>
#include <string>
#include <stdlib.h>
#include "Cipher.hpp"

using namespace std;

int main(int argc, char *argv[]){

	if( argc != 3 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <KEY_FILE>" << endl;
		exit(-1);
	}
    
	ifstream itext;
	ifstream ikey;

	string key;
	string text;
	string cripto;

	itext.open(argv[1]);
	ikey.open(argv[2]);

	getline(ikey,key);
	getline(itext,text);

	itext.close();
	ikey.close();

	VigenereCipher cipher(key);

	cripto = cipher.cipherText(text);

	cout << "PLAIN TEXT" << endl << text << endl << endl;
	cout << "CRIPTO" << endl << cripto << endl << endl;
	cout << "DECRYPTED TEXT" << endl << cipher.decodeText(cripto) << endl;

	return 0;
}
