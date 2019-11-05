
# NetPbImages
small application that reads in net pb images(p1,p2,p3) and transforms them

challenge was to use as little "if"- statements as possible

## No ifs?
this may seem like a random thing to do, but the rational behind it is that it forces you into using a OO design.
because without ifs the only way to change behavior is to use a diffenret implementation of an interface or use polymorphism.

one disclaimer thought: when i wrote this i didn't use streams and lambdas a lot, so maybe this could be refactored with functional programming.


java -jar pbm-tools sample/sample_p1.pbm -rotate -invert sample/out_p1.pbm
