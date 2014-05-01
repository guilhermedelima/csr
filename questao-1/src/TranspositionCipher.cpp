#include "TranspositionCipher.hpp"
#include <algorithm>

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
	return blockOperation(text, CODE);
}


string TranspositionCipher::decodeText(const string& cripto) const{
	return blockOperation(cripto, DECODE);
}


string TranspositionCipher::blockOperation(const string& text, Cipher::operation op) const{

	int i, n_blocks, n_rows, rest;
	string result;

	result.reserve(text.size());

	n_blocks = (int)text.size()%BLOCK_SIZE ? (int)text.size()/BLOCK_SIZE + 1 : (int)text.size()/BLOCK_SIZE;

	for(i=0; i<n_blocks; i++){
		string block( text, i*BLOCK_SIZE, BLOCK_SIZE );

		n_rows = (int) block.size()/KEY_SIZE;
		
		if( (rest=(int)block.size()%KEY_SIZE) ){
				n_rows++;
				block.append(KEY_SIZE-rest, ' ');
		}

		result.append( op == CODE ? cipherBlock(block, n_rows) : decodeBlock(block, n_rows));
	}

	return result;
}


string TranspositionCipher::cipherBlock(const string& block, int rows) const{

	int index;
	string cripto(block);

	for(int j=0; j<KEY_SIZE; j++){
		for(int i=0; i<rows; i++){
			index = (this->key[j]-'1')*rows + i;
			cripto[index] = block[i*KEY_SIZE + j];
		}
	}

	return cripto;
}


string TranspositionCipher::decodeBlock(const string& block, int rows) const{

	int index;
	string plainText(block);

	for(int j=0; j<rows; j++){
		for(int i=0; i<KEY_SIZE; i++){
			index = (this->key[i]-'1')*rows + j;
			plainText[j*KEY_SIZE + i] = block[index];
		}
	}

	return plainText;
}



