
./gradlew bootBuildImage --imageName=iobuilders/bank

docker run -p 8080:8080 iobuilders/bank
