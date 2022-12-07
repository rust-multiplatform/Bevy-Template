FROM rust:latest

USER root
RUN apt-get update && apt-get upgrade -y && apt-get install -y bash build-essential gcc gcc-multilib g++ pkg-config libx11-dev libasound2-dev libudev-dev libwayland-dev libxkbcommon-dev mesa-vulkan-drivers clang lld curl git

USER root
RUN adduser ci --disabled-password --gecos ""

USER ci
WORKDIR /home/ci
CMD [ "bash" ]
