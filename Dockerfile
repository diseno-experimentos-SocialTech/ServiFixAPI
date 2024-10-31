FROM ubuntu:latest
LABEL authors="Luis T"

ENTRYPOINT ["top", "-b"]