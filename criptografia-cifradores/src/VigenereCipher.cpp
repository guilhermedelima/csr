#include "VigenereCipher.hpp"

using namespace std;

VigenereCipher::VigenereCipher(string key) : key(key){

	this->dictionary = new char[DICTIONARY_SIZE];

	//Dicionário para 63 palavras
	for(int i=0; i<26; i++){
		this->dictionary[i] = i + 'a';
		this->dictionary[i+26] = i + 'A';
	}

	for(int i=0; i<10; i++){
		this->dictionary[i + 52] = i + '0';
	}

	this->dictionary[62] = ' ';

//	//Dicionário para Letras maiusculas
//	for(int i=0; i<26; i++){
//		this->dictionary[i] = i + 'A';
//	}
}

VigenereCipher::~VigenereCipher(){
	delete[] dictionary;
}


const int VigenereCipher::DICTIONARY_SIZE = 63;
//const int VigenereCipher::DICTIONARY_SIZE = 26;


int VigenereCipher::getDictionaryPosition(char letter) const{

	int i;

	for(i=0; i<DICTIONARY_SIZE && dictionary[i]!=letter; i++);

	return i;
}


string VigenereCipher::cipherText(const string& text) const{
	return this->textOperation(text, CODE);
}


string VigenereCipher::decodeText(const string& cripto) const{
	return this->textOperation(cripto, DECODE);
}


string VigenereCipher::textOperation(const string& text, Cipher::operation op) const{

	int key_size, index;
	string result(text);

	key_size = (int) this->key.size();

	for(int i=0; i<(int)text.size(); i++){

		if( op == CODE )
			index = ( getDictionaryPosition(text[i]) + getDictionaryPosition(key[i%key_size]) )%DICTIONARY_SIZE;
		else
			index = ( (getDictionaryPosition(text[i]) - getDictionaryPosition(key[i%key_size]))+DICTIONARY_SIZE )%DICTIONARY_SIZE;

		result[i] = dictionary[index];
	}

	return result;
}



