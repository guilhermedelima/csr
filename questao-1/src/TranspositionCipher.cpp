#include "TranspositionCipher.hpp"
#include <algorithm>
#include <iostream>

using namespace std;

const int TranspositionCipher::KEY_SIZE = 8;
const int TranspositionCipher::BLOCK_SIZE = 64;


TranspositionCipher::TranspositionCipher(string key) : Cipher(key){
	if( !this->isValidKey(key) )
		throw KeyException();
}


bool TranspositionCipher::isValidKey(string key){

	int i;

	sort(key.begin(), key.end());

	for(i=0; i<(int)key.size() && key[i]=='1'+i; i++);

	return i == KEY_SIZE && (int)key.size() == KEY_SIZE;
}


string TranspositionCipher::cipherText(const string& text) const{

	int i, n_blocks, n_rows, rest;
	string cripto;

	cripto.reserve(text.size());

	n_blocks = (int)text.size()%BLOCK_SIZE ? (int)text.size()/BLOCK_SIZE + 1 : (int)text.size()/BLOCK_SIZE;

	for(i=0; i<n_blocks; i++){
		string block( text, i*BLOCK_SIZE, BLOCK_SIZE );

		n_rows = (int) block.size()/KEY_SIZE;
		
		if( (rest=(int)block.size()%KEY_SIZE) ){
				n_rows++;
				block.append(KEY_SIZE-rest, '*');
		}

		cripto.append( cipherBlock(block, n_rows) );
	}

	return cripto;
}


//TODO
string TranspositionCipher::decodeText(const string& cripto) const{

	return cripto;
}


string TranspositionCipher::cipherBlock(const string& block, int rows) const{

	int index, count;
	string cripto(block);
	
	count = -1;
	for(int j=0; j<KEY_SIZE; j++){
		for(int i=0; i<rows; i++){
			index = i*KEY_SIZE  + this->key[j]-'1';
			cripto[++count] = block[index];
		}
	}

	return cripto;
}



