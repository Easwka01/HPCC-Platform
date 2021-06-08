
# Display to use for X driver
export DISPLAY=:1

# Remove old driver files
rm driver.log driver.pid

# Start the X video frame buffer in the background
# Simulate a full color HD screen
Xvfb :1 -screen 1 1920x1080x24 &

# Start the chrome driver in the background
chromedriver --log-path=./driver.log &

# Start the testing
mvn test



