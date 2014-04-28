#include "Cipher.hpp"

using namespace std;

const char *KeyException::message = "Key is Invalid";

const char *KeyException::what() const throw(){
	return KeyException::message;
}

Cipher::Cipher(string key){
	this->key = key;
}



