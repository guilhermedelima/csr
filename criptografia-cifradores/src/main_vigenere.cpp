#include <iostream>
#include <fstream>
#include <string>
#include <stdlib.h>
#include "Cipher.hpp"
#include "Hacker.hpp"

using namespace std;

string input =
    "MOMUD EKAPV TQEFM OEVHP AJMII CDCTI FGYAG JSPXY ALUYM NSMYH"
    "VUXJE LEPXJ FXGCM JHKDZ RYICU HYPUS PGIGM OIYHF WHTCQ KMLRD"
    "ITLXZ LJFVQ GHOLW CUHLO MDSOE KTALU VYLNZ RFGBX PHVGA LWQIS"
    "FGRPH JOOFW GUBYI LAPLA LCAFA AMKLG CETDW VOELJ IKGJB XPHVG"
    "ALWQC SNWBU BYHCU HKOCE XJEYK BQKVY KIIEH GRLGH XEOLW AWFOJ"
    "ILOVV RHPKD WIHKN ATUHN VRYAQ DIVHX FHRZV QWMWV LGSHN NLVZS"
    "JLAKI FHXUF XJLXM TBLQV RXXHR FZXGV LRAJI EXPRV OSMNP KEPDT"
    "LPRWM JAZPK LQUZA ALGZX GVLKL GJTUI ITDSU REZXJ ERXZS HMPST"
    "MTEOE PAPJH SMFNB YVQUZ AALGA YDNMP AQOWT UHDBV TSMUE UIMVH"
    "QGVRW AEFSP EMPVE PKXZY WLKJA GWALT VYYOB YIXOK IHPDS EVLEV"
    "RVSGB JOGYW FHKBL GLXYA MVKIS KIEHY IMAPX UOISK PVAGN MZHPW"
    "TTZPV XFCCD TUHJH WLAPF YULTB UXJLN SIJVV YOVDJ SOLXG TGRVO"
    "SFRII CTMKO JFCQF KTINQ BWVHG TENLH HOGCS PSFPV GJOKM SIFPR"
    "ZPAAS ATPTZ FTPPD PORRF TAXZP KALQA WMIUD BWNCT LEFKO ZQDLX"
    "BUXJL ASIMR PNMBF ZCYLV WAPVF QRHZV ZGZEF KBYIO OFXYE VOWGB"
    "BXVCB XBAWG LQKCM ICRRX MACUO IKHQU AJEGL OIJHH XPVZW JEWBA"
    "FWAML ZZRXJ EKAHV FASMU LVVUT TGK";

int main(int argc, char *argv[]){

	if( argc != 3 ){
		cout << "Correct Usage: " << argv[0] << " <TEXT_FILE> <KEY_FILE>" << endl;
		exit(-1);
	}
    
	ifstream itext;
	ifstream ikey;

	string key;
	string text;
	string cripto;

	itext.open(argv[1]);
	ikey.open(argv[2]);

	getline(ikey,key);
	getline(itext,text);

	itext.close();
	ikey.close();

//	//Usado apenas para dicionário de 26
//	for(int i=0; i<(int)cripto.size(); i++){
//		if( cripto[i] == ' ' )
//			cripto.erase(cripto.begin() + i);
//	}

	Cipher* cipher = new VigenereCipher(key);
	Hacker* hacker = new VigenereHacker();

	cripto = cipher->cipherText(text);

	cout << "PLAIN TEXT" << endl << text << endl << endl;
	cout << "CRIPTO" << endl << cripto << endl << endl;
	cout << "DECRYPTED TEXT" << endl << cipher->decodeText(cripto) << endl;


//	for(int i=(int)cripto.size(); i>=0; i--){
//		if( !(i%5) && i)
//			cripto.insert(i, 1, ' ');
//	}

	cout << endl << cripto << endl;
	
	cout << endl << "TEST VIGENERE HACKER" << endl;
	cout << hacker->breakCipher(input) << endl;

	delete cipher;

	return 0;
}
