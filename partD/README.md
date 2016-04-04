Author:
Wanjie Zhang 20601715
Ce Han 20610452 
Fariha Muqtadir  20540275

steps:
1.source code on src/pi/
2.use the format  ./pipair <bitcode file> <T_SUPPORT> <T_CONFIDENCE> | sort > <out_file>
like use ./pipair test3.bc 10 80 | sort >test3_10_80.out 
to get the result in out_file, like vim test3_10_80.out
3.check the result 
check the lock and unlock part.

Build with: javac