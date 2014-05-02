#include "ProductCipher.hpp"

using namespace std;


ProductCipher::ProductCipher(string transpositionKey, string vigenereKey){
	this->transpositionCipher = new TranspositionCipher(transpositionKey);
	this->vigenereCipher = new VigenereCipher(vigenereKey);
}


ProductCipher::~ProductCipher(){
	delete transpositionCipher;
	delete vigenereCipher;
}


string ProductCipher::cipherText(const string& text) const{
	return vigenereCipher->cipherText(  transpositionCipher->cipherText(text)  );
}


string ProductCipher:: decodeText(const string& cripto) const{
	return transpositionCipher->decodeText(  vigenereCipher->decodeText(cripto)  );
}
