.segment "HEADER" ; ROM Header
.ascii "aa" ; Maker Code 
.ascii "aaaa" ; Game Code 
.byte $00, $00, $00, $00, $00, $00, $00 ; Fixed Value 
.byte $00 ; Expansion RAM Size 
.byte $00 ; Special Version 
.byte $00 ; Cartridge Type subnumber 
.ascii "aaaaaaaaaaa          " ; Game Title 
.byte $20 ; Map Mode 
.byte $00 ; Cartridge Type 
.byte $00 ; ROM Size 
.byte $00 ; RAM Size 
.byte $01 ; Destination Code 
.byte $33 ; Fixed Value 
.byte $00 ; Revision 
.byte $ff, $ff ; Complement Check 
.byte $00, $00 ; Checksum 
