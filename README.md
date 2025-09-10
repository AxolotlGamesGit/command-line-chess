# command-line-chess
Simple chess program in the command line

## Commands
unix-chess
- new
- quit
- move
   - [algebraic chess notation]
   - [square1->square2 (a1->b2)]
   - --undo
- save
   - --pgn
   - --fen
- load
   - --pgn
   - --fen
- settings
   - --showrank [true/false]
   - --showfile [true/false]

## Project Status
Right now, the "new", "quit" and "settings" commands work, and the "move" command with custom notation works. Right now en passant, castling, checks, and checkmate don't work.

## Example
```
h@penguin:~$ javac CommandLineChess.java
h@penguin:~$ java CommandLineChess
h@penguin:~$ new

r n b q k b n r 8
p p p p p p p p 7
                6
                5
                4
                3
P P P P P P P P 2
R N B Q K B N R 1
a b c d e f g h
White to move

h@penguin:~$ settings --showrank false --showfile false

r n b q k b n r
p p p p p p p p
               
               
               
               
P P P P P P P P
R N B Q K B N R
White to move

h@penguin:~$ move e4

r n b q k b n r
p p p p p p p p


        P
     
P P P P   P P P
R N B Q K B N R
Black to move

h@penguin:~$ move e2->e5

r n b q k b n r
p p p p   p p p

        p
        P
     
P P P P   P P P
R N B Q K B N R
White to move

h@penguin:~$ move Nf3

r n b q k b n r
p p p p   p p p

        p
        P
          N
P P P P   P P P
R N B Q K B   R
Black to move

h@penguin:~$ save --pgn

1. e4 e5
2. Nf3

h@penguin:~$ load --pgn “1. e4 e5 2. Nf3 Nc6”

r   b q k b n r
p p p p   p p p
    n
        p
        P
          N
P P P P   P P P
R N B Q K B   R
White to move

h@penguin:~$ quit
```
