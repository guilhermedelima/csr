#include "TranspositionCipher.hpp"

using namespace std;

TranspositionCipher::TranspositionCipher(string key) : Cipher(key){

}


string TranspositionCipher::cipherText(const string& text) const{

	return text;
}



void TranspositionCipher::cipherBlock(string& block) const{


}

