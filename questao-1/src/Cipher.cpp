#include "Cipher.hpp"

using namespace std;

const char *KeyException::MESSAGE = "Key is Invalid";

const char *KeyException::what() const throw(){
	return KeyException::MESSAGE;
}



