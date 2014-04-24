#include <iostream>
#include <fstream>
#include <string>
#include "cipher.hpp"

using namespace std;

int main(int argc, char *argv[]){
    
    ifstream itext;
    ifstream ikey;
    
	string key;
	string text;
	string cripto;

    cout << argc << endl;
    
    if(argc != 3){
       key = "CHAVEVERNAMCRIPTOGRAFIA";
	   text = "Texto Em claro para cifracao ANO 2014";
    } else {
        itext.open(argv[1]);
        ikey.open(argv[2]);
        
        getline(ikey,key);
        getline(itext,text);

        itext.close();
        ikey.close();
    }
    
	VernamCipher cipher(key);
	
	cripto = cipher.cipherText(text);

	cout << text << endl << endl;
	cout << cripto << endl << endl;
	cout << cipher.decodeText(cripto) << endl;

	return 0;
}
