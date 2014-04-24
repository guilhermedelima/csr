#include "Util.hpp"

string Util::convertToBin(unsigned char x){

	int i;
	string bin;

	bin.resize(8, '0');

	for(i=7; i>=0 && x; i--){
		bin[i] = x%2 + '0';
		x /= 2;
	}

	return bin;
}


unsigned char Util::convertToChar(string bin){

	int i;
	unsigned char c;

	for(i=0, c=0; i<8; i++){
		c = c <<1 | (bin[i]-'0');
	}

	return c;
}
