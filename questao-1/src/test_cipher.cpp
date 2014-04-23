#include <iostream>
#include <string>
#include "cipher.hpp"

using namespace std;

int main(void){

	string key;
	string text;
	string cripto;

	key = "CHAVEVERNAMCRIPTOGRAFIA";
	text = "Texto Em claro para cifracao ANO 2014";

	VernamCipher cipher(key);
	
	cripto = cipher.cipherText(text);

	cout << text << endl << endl;
	cout << cripto << endl << endl;
	cout << cipher.decodeText(cripto) << endl;

	return 0;
}
