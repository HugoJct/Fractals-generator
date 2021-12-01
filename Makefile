all: build run
build:
	./gradlew build
run: build
	./gradlew run
custom: clean
	./gradlew run --console=plain --args="$(MAKECMDGOALS)"

clean:
	rm -r build/classes