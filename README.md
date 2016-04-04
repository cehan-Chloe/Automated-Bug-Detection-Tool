# Automated-Bug-Detection-Tool

This tool is based on likely-invariants from call graphs in a way that is similar to the SOSP'01 paper discussed in ECE 653 Software Test/Qual Assur/Maint (Coverity). In particular, this tool is focusing those pairs of functions are called together. It will then use these likely-invariants to automatically detect software bugs.

Building an Automated Bug Detection Tool:

Part(A)Inferring Likely Invariants for Bug Detection.
Part(B)Finding and Explaining False Positives.
Part(C)Inter-Procedural Analysis.
Part(D)Improving the Solutions.Propose another solution to reduce false positives and one solution to find more bugs.

Using a Static Bug Detection Tool, Coverity, to Analyzing Your Own Code.

Build

Use Makefile to build the program. The Makefile in every part provides 'all' and 'clean' operation. The path are definite path.