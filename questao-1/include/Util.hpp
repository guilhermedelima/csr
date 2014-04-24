#ifndef UTIL_HPP
#define UTIL_HPP

#include <string>

using namespace std;

class Util{
	public:
		static string convertToBin(unsigned char x);
		static unsigned char convertToChar(string bin);
};

#endif
