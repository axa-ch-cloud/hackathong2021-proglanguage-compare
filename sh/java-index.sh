sleep 5

time sh java-test.sh

kill -SIGTERM $(pidof java)
