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

	int i, key_size, index;
	string cripto(text);

	key_size = (int) this->key.size();

	for(i=0; i<(int)text.size(); i++){
		index = ( this->getDictionaryPosition(text[i]) + this->getDictionaryPosition(key[i%key_size]) )%DICTIONARY_SIZE;

		cripto[i] = dictionary[index];
	}

	return cripto;
}


string VigenereCipher::decodeText(const string& cripto) const{
	int i, key_size, index;
	string plain_text(cripto);

	key_size = (int) this->key.size();

	for(i=0; i<(int)cripto.size(); i++){
		index = getDictionaryPosition(cripto[i]) - getDictionaryPosition(key[i%key_size]);

		if( index<0 )
			index += DICTIONARY_SIZE;

		plain_text[i] = dictionary[index];
	}

	return plain_text;
}


