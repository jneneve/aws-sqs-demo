#!/bin/bash

set -e

# Create SQS queue
awslocal sqs create-queue --queue-name my-queue