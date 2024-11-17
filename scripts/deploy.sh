#!/bin/bash
set -e

# Ensure required environment variables are set
if [ -z "$EC2_HOST" ] || [ -z "$EC2_USER" ] || [ -z "$EC2_KEY" ]; then
    echo "Error: EC2_HOST, EC2_USER, and EC2_KEY environment variables must be set."
    exit 1
fi

# Clean up the key file when the script exits
trap 'rm -f ec2_key.pem' EXIT

echo "Deploying to EC2 instance at $EC2_HOST"

# Create a new key file with the contents of the EC2_KEY secret
echo "$EC2_KEY" > ec2_key.pem
chmod 400 ec2_key.pem

# Copy the files to the EC2 instance
scp -o StrictHostKeyChecking=no -i ec2_key.pem target/*.jar $EC2_USER@$EC2_HOST:/home/$EC2_USER/app.jar
if [ $? -ne 0 ]; then
    echo "Error: Failed to copy files to EC2 instance."
    exit 1
fi

# SSH into the EC2 instance and restart the server
ssh -o StrictHostKeyChecking=no -i ec2_key.pem $EC2_USER@$EC2_HOST <<EOF
    # Stop the existing server
    pkill -f 'app.jar' || true

    # Start the new server
    nohup java -jar /home/$EC2_USER/app.jar > app.log 2>&1 &
EOF
if [ $? -ne 0 ]; then
    echo "Error: Failed to execute commands on EC2 instance."
    exit 1
fi

echo "Deployment complete"
