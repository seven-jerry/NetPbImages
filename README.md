
# NetPbImages
small application that reads in net pb images(p1,p2,p3) and transforms them(rotate,invert)

challenge was to use no "if"- statements in the code

## No ifs - but why?
this may seem like a random thing to do, but the rational behind it was that it forces you into using a OO design.
because without ifs the only way to change behavior is to use a diffenret implementation of an interface or use polymorphism.



java -jar pbm-tools sample/sample_p1.pbm -rotate -invert sample/out_p1.pbm
