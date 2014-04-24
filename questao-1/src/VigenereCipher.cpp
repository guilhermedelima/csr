#include "VigenereCipher.hpp"

using namespace std;

VigenereCipher::VigenereCipher(string key) : Cipher(key){

	this->dictionary = new char[DICTIONARY_SIZE];

	for(int i=0; i<26; i++){
		this->dictionary[i] = i + 'a';
		this->dictionary[i+26] = i + 'A';
	}

	for(int i=0; i<10; i++){
		this->dictionary[i + 52] = i + '0';
	}

	this->dictionary[62] = ' ';
}

VigenereCipher::~VigenereCipher(){
	delete[] dictionary;
}


const int VigenereCipher::DICTIONARY_SIZE = 63;


int VigenereCipher::getDictionaryPosition(char letter) const{

    for(int i=0;;i++){
        if(this->dictionary[i] == letter)
            return i;
    }

	return -1;
}

string VigenereCipher::cipherText(const string& text) const{

	int i, key_size;
	string cripto(text);

	key_size = (int) this->key.size();

	for(i=0; i<(int)text.size(); i++){
		cripto[i] = dictionary[(getDictionaryPosition(text[i]) + getDictionaryPosition(key[i%key_size]))%DICTIONARY_SIZE];
	}

	return cripto;
}


string VigenereCipher::decodeText(const string& cripto) const{
	int i, key_size;
	string _cripto(cripto);

	key_size = (int) this->key.size();

	for(i=0; i<(int)cripto.size(); i++){
		_cripto[i] = dictionary[getDictionaryPosition(cripto[i]) - getDictionaryPosition(key[i%key_size])];
	}

	return _cripto;
}


