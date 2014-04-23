#include "cipher.hpp"

Cipher::Cipher(string key){
	this->key = key;
}


VernamCipher::VernamCipher(string key) : Cipher(key){
}


string VernamCipher::cipherText(const string& text) const{

	int i, key_size;
	string cripto(text);

	key_size = (int) this->key.size();

	for(i=0; i<(int)text.size(); i++){
		cripto[i] = text[i] ^ this->key[i%key_size];
	}

	return cripto;
}

string VernamCipher::decodeText(const string& cripto) const{
	return this->cipherText(cripto);
}
