all: build run
build:
	./gradlew build
run: build
	./gradlew run
