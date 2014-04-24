SRCDIR=src
BINDIR=bin
INCDIR=include
OBJDIR=obj
RSCDIR=resources

CC=g++
CFLAGS=-W -Wall -pedantic -ansi -I $(INCDIR)

TARGET=cipher

_HEADER=$(foreach header,$(shell ls $(INCDIR)),$(header))
_OBJ=$(_HEADER:.hpp=.o) test_cipher.o

HEADER=$(patsubst %,$(INCDIR)/%,$(_HEADER))
OBJ=$(patsubst %,$(OBJDIR)/%,$(_OBJ))


####### TARGETS ########

all: $(TARGET)

$(OBJDIR)/%.o: $(SRCDIR)/%.cpp $(HEADER)
	$(CC) $(CFLAGS) -c -o $@ $<

$(TARGET): $(OBJ)
	$(CC) $(CFLAGS) -o $(BINDIR)/$@ $^

clean:
	@ rm -f *~ *.out
	@ rm -f $(OBJDIR)/*
	@ rm -f $(BINDIR)/*
	@ rm -f $(SRCDIR)/*~
	@ rm -f $(INCDIR)/*~
	@ rm -f $(INCDIR)/*~
	@ rm -f $(RSCDIR)/*~
