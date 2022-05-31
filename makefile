JC = javac 
FLAGS = -d
run:
	$(JC) $(FLAGS) classes/ *.java
	java -classpath classes Application localhost:9090 localhost:9091 localhost:9092 cielo Dios infierno Jehová tierra señor purgatorio Israel profeta muerte enfermedad pueblo hombre mujer
clean:
	rm ./classes/*.class