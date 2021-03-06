SRCDIR=src
BINDIR=bin
INCDIR=include
OBJDIR=obj
RSCDIR=resources

CC=g++
CFLAGS=-W -Wall -pedantic -ansi -I $(INCDIR) -std=c++0x

VIGENERE=vigenere
TRANSPOSITION=transposition
PRODUCT=product

_HEADER=$(foreach header,$(shell ls $(INCDIR)),$(header))
_OBJ1=$(_HEADER:.hpp=.o) main_$(VIGENERE).o
_OBJ2=$(_HEADER:.hpp=.o) main_$(TRANSPOSITION).o
_OBJ3=$(_HEADER:.hpp=.o) main_$(PRODUCT).o

HEADER=$(patsubst %,$(INCDIR)/%,$(_HEADER))
OBJ1=$(patsubst %,$(OBJDIR)/%,$(_OBJ1))
OBJ2=$(patsubst %,$(OBJDIR)/%,$(_OBJ2))
OBJ3=$(patsubst %,$(OBJDIR)/%,$(_OBJ3))


####### TARGETS ########

all: $(VIGENERE) $(TRANSPOSITION) $(PRODUCT)

$(OBJDIR)/%.o: $(SRCDIR)/%.cpp $(HEADER)
	$(CC) $(CFLAGS) -c -o $@ $<

$(VIGENERE): $(OBJ1)
	$(CC) $(CFLAGS) -o $(BINDIR)/$@ $^

$(TRANSPOSITION): $(OBJ2)
	$(CC) $(CFLAGS) -o $(BINDIR)/$@ $^

$(PRODUCT): $(OBJ3)
	$(CC) $(CFLAGS) -o $(BINDIR)/$@ $^

clean:
	@ rm -f *~ *.out
	@ rm -f $(OBJDIR)/*
	@ rm -f $(BINDIR)/*
	@ rm -f $(SRCDIR)/*~
	@ rm -f $(INCDIR)/*~
	@ rm -f $(INCDIR)/*~
	@ rm -f $(RSCDIR)/*~
